package com.shoppproduct.dream_shops.exception;

public class CartNotFoundException extends RuntimeException{
    
    public CartNotFoundException(String message){
        super(message);
    }

}
