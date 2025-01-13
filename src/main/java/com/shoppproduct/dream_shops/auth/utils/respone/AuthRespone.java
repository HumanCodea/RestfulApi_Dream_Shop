package com.shoppproduct.dream_shops.auth.utils.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRespone {
    
    private String accessToken;

    private String refreshToken;

}
