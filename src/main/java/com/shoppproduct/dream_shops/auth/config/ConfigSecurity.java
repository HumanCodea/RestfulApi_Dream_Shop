package com.shoppproduct.dream_shops.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shoppproduct.dream_shops.auth.service.AuthFilterService;
import com.shoppproduct.dream_shops.auth.service.JwtAuthEntryPoint;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
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
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auths/**","/api/v1/forgotPassword/**").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(authEntryPoint)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authFilterService, UsernamePasswordAuthenticationFilter.class); // ưu tiên hơn exceptionHandling

        return httpSecurity.build();
                
    }

}
