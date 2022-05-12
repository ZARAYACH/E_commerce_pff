package com.Ecommerce.Security;


import com.Ecommerce.Logs.Logs;
import com.Ecommerce.Logs.LogsRepo;
import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final LogsRepo logsRepo;
    private final UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = userRepo.getUserByEmail(authentication.getName());
        var details = (WebAuthenticationDetails)authentication.getDetails();
        request.getSession().setAttribute("user",user);
        Logs log = new Logs();//TODO :fix this logs
        logsRepo.save(log);

    }
}
