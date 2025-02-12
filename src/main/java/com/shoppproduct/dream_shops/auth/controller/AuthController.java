package com.shoppproduct.dream_shops.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppproduct.dream_shops.auth.model.RefreshToken;
import com.shoppproduct.dream_shops.auth.model.User;
import com.shoppproduct.dream_shops.auth.service.AuthService;
import com.shoppproduct.dream_shops.auth.service.JwtService;
import com.shoppproduct.dream_shops.auth.service.RefreshTokenService;
import com.shoppproduct.dream_shops.auth.utils.request.LoginRequest;
import com.shoppproduct.dream_shops.auth.utils.request.RefreshTokenRequest;
import com.shoppproduct.dream_shops.auth.utils.request.RegisterRequest;
import com.shoppproduct.dream_shops.auth.utils.respone.AuthRespone;
import com.shoppproduct.dream_shops.exception.ReTokenExpiredException;
import com.shoppproduct.dream_shops.utils.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "${api.prefix}/auths")
@Tag(name = "Auth Controller")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    @Operation(summary = "Register account", description = "Api to register new account")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(new ApiResponse("Register Successfully!", authService.register(registerRequest)));
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login account", description = "Api to login account with email and password")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest){
        try {
            return ResponseEntity.ok(new ApiResponse("Login Successfully!", authService.login(loginRequest)));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/refreshToken")
    @Operation(summary = "Refresh token", description = "Api to refresh token")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){

        try {
            RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());

            User user = refreshToken.getUser();

            String accessToken = jwtService.generateToken(user);

            return ResponseEntity.ok(new ApiResponse("Refresh Token Successfully", AuthRespone.builder()
                                                                        .accessToken(accessToken)
                                                                        .refreshToken(refreshToken.getRefreshToken())
                                                                        .build()));
        } catch (ReTokenExpiredException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }


}
