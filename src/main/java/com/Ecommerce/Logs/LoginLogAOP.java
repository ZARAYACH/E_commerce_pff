package com.Ecommerce.Logs;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginLogAOP {

    @After("execution(* com.Ecommerce.Jwts.CustomAuthenticationFilter.successAuth())")
    public void afteReturn(JoinPoint joinPoint) throws Throwable {
        System.out.println("ddd");

    }
}
