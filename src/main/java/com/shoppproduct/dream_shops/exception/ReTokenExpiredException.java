package com.shoppproduct.dream_shops.exception;

public class ReTokenExpiredException extends RuntimeException{
    public ReTokenExpiredException(String message){
        super(message);
    }
}
