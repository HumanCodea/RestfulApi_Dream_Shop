package com.shoppproduct.dream_shops.auth.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shoppproduct.dream_shops.auth.model.RefreshToken;
import com.shoppproduct.dream_shops.auth.model.User;
import com.shoppproduct.dream_shops.auth.repository.RefreshTokenRepository;
import com.shoppproduct.dream_shops.auth.repository.UserRepository;
import com.shoppproduct.dream_shops.auth.service.Imp.IRefreshTokenService;
import com.shoppproduct.dream_shops.exception.ReTokenExpiredException;
import com.shoppproduct.dream_shops.exception.ReTokenNotFoundException;

@Service
public class RefreshTokenService implements IRefreshTokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(String username) {
        
        User user = Optional.ofNullable(userRepository.findByEmail(username))
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + username));

        RefreshToken refreshToken = user.getRefreshToken();

        if ( refreshToken == null ) {
            long refreshTokenValidity = 5*60*60*1000;
            refreshToken = RefreshToken
                    .builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();

            refreshTokenRepository.save(refreshToken);
        }

        return refreshToken;

    }

    @Override
    public RefreshToken verifyRefreshToken(String refreshToken) {
       
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ReTokenNotFoundException("Not found refresh token!"));

        if ( refToken.getExpirationTime().compareTo(Instant.now()) < 0 ) {
            refreshTokenRepository.delete(refToken);
            throw new ReTokenExpiredException("Refresh token expired! Please login again");
        }

        return refToken;

    }
    
    

}
