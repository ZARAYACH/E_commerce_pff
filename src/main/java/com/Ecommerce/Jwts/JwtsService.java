package com.Ecommerce.Jwts;

import com.Ecommerce.Logs.Logs;
import com.Ecommerce.Logs.LogsService;
import com.Ecommerce.UserCredentiels.UserCredentials;
import com.Ecommerce.UserCredentiels.UserCredentialsRepo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
@Component
public class JwtsService {

    @Autowired
    private UserCredentialsRepo userCredentialsRepo;

    @Autowired
    private LogsService logsService;

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

    public String createJwtRefreshToken(HttpServletRequest request,User user){
        Algorithm algorithmRefresh = Algorithm.HMAC256("refreshrefreshrefreshrefreshrefreshrefreshrefresh".getBytes(StandardCharsets.UTF_8));
        String refrechToken = JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(Date.valueOf(LocalDate.now()))
                .withExpiresAt(Date.valueOf(LocalDate.now().plusMonths(4)))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithmRefresh);
        Logs log = new Logs();
//        log.setLoginTime(LocalDateTime.now());
//        log.setRefreshToken(refrechToken);
//        log.setIpAddress(request.getRemoteAddr());
//        log.setUserAgent(request.getHeader("User-Agent") );
//        com.Ecommerce.User.User appUser = new com.Ecommerce.User.User();
//        appUser.setEmail(user.getUsername());
//        log.setUser(appUser);
//        logsService.addLog(log);
        return refrechToken;
    }

    public void getAccessTokenByRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshTokenHeader = request.getHeader("refreshToken");
        if (refreshTokenHeader !=null && refreshTokenHeader.startsWith("Bearer ")){
            String refresh_token =   refreshTokenHeader.substring("Bearer ".length());
            Algorithm algorithmRefresh = Algorithm.HMAC256("refreshrefreshrefreshrefreshrefreshrefreshrefresh".getBytes(StandardCharsets.UTF_8));
            try {
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

}
