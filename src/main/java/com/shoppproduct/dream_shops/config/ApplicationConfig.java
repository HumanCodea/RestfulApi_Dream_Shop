package com.shoppproduct.dream_shops.config;

import java.util.List;
import java.util.Optional;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shoppproduct.dream_shops.auth.repository.UserRepository;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class ApplicationConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> Optional.ofNullable(userRepository.findByEmail(username))
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email = " + username));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OpenAPI openAPI(@Value("${open.api.title}") String title,
                        @Value("${open.api.version}") String version,
                        @Value("${open.api.description}") String description,
                        @Value("${open.api.serverUrl}") String serverUrl,
                        @Value("${open.api.serverName}") String serverName){
        return new OpenAPI().info(new Info().title(title)
                        .version(version)
                        .description(description)
                        .license(new License().name("API License").url("http://doman.vn/license")))
                    .servers(List.of(new Server().url(serverUrl).description(serverName)))
                    .components(
                        new Components()
                            .addSecuritySchemes(
                                "bearerAuth", 
                                new SecurityScheme()
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")))
                    .security(List.of(new SecurityRequirement().addList("bearerAuth")));
    }

    @Bean
    public GroupedOpenApi groupedOpenApiProduct(){
        return GroupedOpenApi.builder()
                .group("api-service-product")
                .packagesToScan("com.shoppproduct.dream_shops.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiAuth(){
        return GroupedOpenApi.builder()
                .group("api-service-auth")
                .packagesToScan("com.shoppproduct.dream_shops.auth.controller")
                .build();
    }

}
