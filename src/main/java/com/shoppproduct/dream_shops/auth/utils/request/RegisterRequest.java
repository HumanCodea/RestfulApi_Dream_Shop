package com.shoppproduct.dream_shops.auth.utils.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
