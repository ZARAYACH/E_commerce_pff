package com.Ecommerce.Security;

import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRoles;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,accessDeniedException.getMessage());
        System.out.println(request.getUserPrincipal());
        System.out.println(request.isUserInRole(String.valueOf(UserRoles.CUSTOMER)));
        System.out.println(accessDeniedException.getCause());
    }
}
