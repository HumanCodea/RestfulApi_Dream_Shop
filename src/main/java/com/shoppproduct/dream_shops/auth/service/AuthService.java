package com.shoppproduct.dream_shops.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shoppproduct.dream_shops.auth.model.RefreshToken;
import com.shoppproduct.dream_shops.auth.model.User;
import com.shoppproduct.dream_shops.auth.repository.UserRepository;
import com.shoppproduct.dream_shops.auth.service.Imp.IRefreshTokenService;
import com.shoppproduct.dream_shops.auth.utils.dto.UserRole;
import com.shoppproduct.dream_shops.auth.utils.request.LoginRequest;
import com.shoppproduct.dream_shops.auth.utils.request.RegisterRequest;
import com.shoppproduct.dream_shops.auth.utils.respone.AuthRespone;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRefreshTokenService iRefreshTokenService;

    @Autowired
    private JwtService jwtService;

    public AuthRespone register(RegisterRequest registerRequest){
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .userRole(UserRole.USER)
                .build();

            User savedUser = userRepository.save(user);
            String accessToken = jwtService.generateToken(savedUser);
            RefreshToken refreshToken = iRefreshTokenService.createRefreshToken(savedUser.getEmail());
        
            return AuthRespone.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken.getRefreshToken())
                        .build();
        
        }

    public AuthRespone login(LoginRequest loginRequest){

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), 
                loginRequest.getPassword())
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Not found email"));
        
        
        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = iRefreshTokenService.createRefreshToken(user.getEmail());

    
        return AuthRespone.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();

    }
    
}
