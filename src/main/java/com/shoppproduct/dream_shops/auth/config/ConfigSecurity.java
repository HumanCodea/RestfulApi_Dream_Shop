package com.shoppproduct.dream_shops.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shoppproduct.dream_shops.auth.service.AuthFilterService;
import com.shoppproduct.dream_shops.auth.service.JwtAuthEntryPoint;

@Configuration
public class ConfigSecurity {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthFilterService authFilterService;

    @Autowired
    private JwtAuthEntryPoint authEntryPoint;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(crsf -> crsf.disable())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auths/**", "/api/v1/users/createUser").permitAll()
                .anyRequest().authenticated())
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authFilterService, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
                
    }

}
