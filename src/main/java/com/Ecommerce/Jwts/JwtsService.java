package com.Ecommerce.Jwts;


import com.Ecommerce.Logs.Logs;
import com.Ecommerce.Logs.LogsRepo;
import com.Ecommerce.UserCredentiels.UserCredentials;
import com.Ecommerce.UserCredentiels.UserCredentialsRepo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class JwtsService {

    @Autowired
    private UserCredentialsRepo userCredentialsRepo;

    @Autowired
    private LogsRepo logsRepo;

    public String createJwtAccessToken(HttpServletRequest request, User user){
        Algorithm algorithmAccess = Algorithm.HMAC256("secretsecretsecretsecretsecretsecretsecret".getBytes(StandardCharsets.UTF_8));
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withIssuedAt(Date.valueOf(LocalDate.now()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithmAccess);
    }

    public String createJwtRefreshToken(HttpServletRequest request, HttpServletResponse response, User user,String accessToken) throws JsonProcessingException {
        Algorithm algorithmRefresh = Algorithm.HMAC256("refreshrefreshrefreshrefreshrefreshrefreshrefresh".getBytes(StandardCharsets.UTF_8));
        String refrechToken = JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(Date.valueOf(LocalDate.now()))
                .withExpiresAt(Date.valueOf(LocalDate.now().plusMonths(4)))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithmRefresh);
        Logs log = new Logs();
        log.setRefreshToken(refrechToken);
        log.setIpAddress(request.getRemoteAddr());
        log.setLoginTime(LocalDateTime.now());
        log.setLogoutTime(null);
        log.setUserAgent(request.getHeader("User-Agent") );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String jsonLog = objectMapper.writeValueAsString(log);
        sendPOSTRequest("http://localhost:8081/api/v1/logs/add",jsonLog,accessToken);
        return refrechToken;
    }

    public void getAccessTokenByRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshTokenHeader = request.getHeader("refreshToken");
        if (refreshTokenHeader !=null && refreshTokenHeader.startsWith("Bearer ")){
            String refresh_token = refreshTokenHeader.substring("Bearer ".length());
            Algorithm algorithmRefresh = Algorithm.HMAC256("refreshrefreshrefreshrefreshrefreshrefreshrefresh".getBytes(StandardCharsets.UTF_8));
            try {
               if (logsRepo.existsByRefreshTokenAndAndLogoutTimeIsNull(refresh_token)){
                   JWTVerifier verifier = JWT.require(algorithmRefresh).build();
                   DecodedJWT decodedJWT = verifier.verify(refresh_token);
                   String email = decodedJWT.getSubject();
                   UserCredentials userCredentials = userCredentialsRepo.getByEmail(email);
                   User user = new User(userCredentials.getEmail(),
                           userCredentials.getPassword(),
                           userCredentials.isEnabled(),
                           userCredentials.isCredentialsNonExpired(),
                           userCredentials.isCredentialsNonExpired(),
                           userCredentials.isAccountNonLocked(),
                           userCredentials.getAuthorities());
                   String access_token = createJwtAccessToken(request,user);
                   Map<String, String> tokens = new HashMap<>();
                   tokens.put("access_token", access_token);
                   tokens.put("refresh_token", refresh_token);
                   response.setContentType(APPLICATION_JSON_VALUE);
                   new ObjectMapper().writeValue(response.getOutputStream(), tokens);
               }else{
                   response.setStatus(FORBIDDEN.value());
                   response.setContentType(APPLICATION_JSON_VALUE);
                   HashMap<String,String> error = new HashMap<>();
                   error.put("error","this refresh token is already been destroyed");
                   new ObjectMapper().writeValue(response.getOutputStream(),error.toString());
               }
            }catch (Exception e){
                response.setStatus(FORBIDDEN.value());
                response.setContentType(APPLICATION_JSON_VALUE);
                HashMap<String,String> error = new HashMap<>();
                error.put("error",e.getMessage());
                System.out.println(e.getMessage());
                new ObjectMapper().writeValue(response.getOutputStream(),error.toString());
                e.printStackTrace();

            }
        }
    }
    public static JsonObject sendPOSTRequest(String urlAccessd, String body,String accesToken) {
        try {
            String post_data = body;

            URL url = new URL(urlAccessd);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            //adding header
            httpURLConnection.setRequestProperty("Authorization","Bearer "+accesToken);
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);


            //Adding Post Data
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(post_data.getBytes());
            outputStream.flush();
            outputStream.close();


            System.out.println("Response Code " + httpURLConnection.getResponseCode());

//            String line = "";
//            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            StringBuilder response = new StringBuilder();
//            while ((line = bufferedReader.readLine()) != null) {
//                response.append(line);
//            }
//            bufferedReader.close();
//            Gson gson1 = new Gson();
//            JsonObject object = gson1.fromJson(response.toString(), JsonObject.class);
//            return object;


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Making POST Request");
        }
        return null;
    }

}
