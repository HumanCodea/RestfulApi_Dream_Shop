package com.shoppproduct.dream_shops.auth.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthFilterService extends OncePerRequestFilter{

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Bỏ qua các đường dẫn cho phép
        if (path.startsWith("/api/v1/auths/")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            String jwt = parseJwt(request);

            String username = jwtService.extractUsername(jwt);

            if ( username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null,
                        userDetails.getAuthorities());

                    authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(": Invalid or expired token, you may login again or refresh token");
            return;
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request){
        
        final String authHeader = request.getHeader("Authorization");
        if ( StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ") ) {
            return authHeader.substring(7);
        }

        return null;
    }
    
}
