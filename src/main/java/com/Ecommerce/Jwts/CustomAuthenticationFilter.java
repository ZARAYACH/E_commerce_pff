package com.Ecommerce.Jwts;


import com.Ecommerce.Logs.Logs;
import com.Ecommerce.Logs.LogsRepo;
import com.Ecommerce.Logs.LogsService;
import com.Ecommerce.User.UserRepo;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.*;

@AllArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private JwtsService jwtsService ;

    private LogsRepo logsRepo;
    private UserRepo userRepo;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            EmailPasswordModal emailPasswordModal = new ObjectMapper().readValue(request.getInputStream(), EmailPasswordModal.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(emailPasswordModal.getEmail(),
                    emailPasswordModal.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;

        } catch (IOException e) {
            response.setStatus(BAD_REQUEST.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            System.out.println(e.getMessage());
            try {
                new ObjectMapper().writeValue(response.getOutputStream(), error.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String access_token = jwtsService.createJwtAccessToken(request, user);
        String refresh_token = jwtsService.createJwtRefreshToken(request,response, user,access_token);
        Logs log = new Logs();
        log.setRefreshToken(refresh_token);
        log.setIpAddress(request.getRemoteAddr());
        log.setLoginTime(LocalDateTime.now());
        com.Ecommerce.User.User appUser = userRepo.findUserByEmail(user.getUsername());
        appUser.setLogedIn(true);
        log.setUser(appUser);
        log.setLogoutTime(null);
        log.setUserAgent(request.getHeader("User-Agent") );
        logsRepo.save(log);
        userRepo.save(appUser);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        HashMap<String, String> error = new HashMap<>();
        logger.info(failed.toString());
        error.put("error", "email or password is invalid");
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }


    //TODO::make a post request to the endpoint responsable of adding a log and call it in the sussful authentification
    public void makeHttpPostRequest() {

    }
}