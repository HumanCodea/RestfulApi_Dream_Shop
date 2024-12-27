package com.shoppproduct.dream_shops.exception;

public class ProductNotFoundException extends RuntimeException{
    
    public ProductNotFoundException(String message){
        super(message);
    }

}
