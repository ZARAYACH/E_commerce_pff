package com.Ecommerce.Security;


import com.Ecommerce.Jwts.CustomAuthenticationFilter;
import com.Ecommerce.Jwts.CustomAuthorizationFilter;
import com.Ecommerce.Jwts.JwtsService;
import com.Ecommerce.Logs.Logs;
import com.Ecommerce.Logs.LogsRepo;
import com.Ecommerce.User.UserRepo;
import com.Ecommerce.User.UserRoles;
import com.Ecommerce.UserCredentiels.UserCredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    private final UserCredentialsService userCredentialsService;
    private final BCryptPasswordEncoder passwordEncoder ;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint ;
    private final AccessDeniedHandler accessDeniedExceptionHandler;
    private final UserRepo userRepo;
    private final JwtsService jwtsService;
    private final LogsRepo logsRepo;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(this.restAuthenticationEntryPoint)
                .accessDeniedHandler((AccessDeniedHandler) this.accessDeniedExceptionHandler)
                .and()
                .addFilter(new CustomAuthenticationFilter(authenticationManager(),jwtsService,logsRepo,userRepo))
                .addFilterBefore(new CustomAuthorizationFilter(userRepo), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v1/product/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/api/v1/user/signup").permitAll()
                .antMatchers("/api/v1/token/refresh").permitAll()
                .antMatchers("/api/v1/verifyAccount").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/api/v1/user/**").hasAnyAuthority("CUSTOMER","ADMIN")
                .antMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userCredentialsService).passwordEncoder(passwordEncoder);
    }


}
