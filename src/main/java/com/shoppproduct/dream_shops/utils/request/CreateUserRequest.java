package com.shoppproduct.dream_shops.utils.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
