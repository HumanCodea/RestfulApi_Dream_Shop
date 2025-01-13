package com.shoppproduct.dream_shops.auth.service.Imp;

import com.shoppproduct.dream_shops.auth.model.RefreshToken;

public interface IRefreshTokenService {
    
    RefreshToken createRefreshToken(String username);

    RefreshToken verifyRefreshToken(String refreshToken);

}
