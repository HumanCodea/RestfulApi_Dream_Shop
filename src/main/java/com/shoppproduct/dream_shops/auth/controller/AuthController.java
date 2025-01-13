package com.shoppproduct.dream_shops.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppproduct.dream_shops.auth.service.AuthService;
import com.shoppproduct.dream_shops.auth.utils.request.LoginRequest;
import com.shoppproduct.dream_shops.auth.utils.request.RegisterRequest;
import com.shoppproduct.dream_shops.utils.response.ApiResponse;

@RestController
@RequestMapping(path = "${api.prefix}/auths")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(new ApiResponse("Register Successfully!", authService.register(registerRequest)));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(new ApiResponse("Login Successfully!", authService.login(loginRequest)));
    }

}
