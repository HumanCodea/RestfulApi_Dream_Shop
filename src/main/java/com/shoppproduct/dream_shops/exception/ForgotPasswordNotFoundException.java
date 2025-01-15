package com.shoppproduct.dream_shops.exception;

public class ForgotPasswordNotFoundException extends RuntimeException{
    public ForgotPasswordNotFoundException(String message){
        super(message);
    }
}
