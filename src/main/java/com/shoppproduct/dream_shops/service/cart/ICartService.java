package com.shoppproduct.dream_shops.service.cart;

import java.math.BigDecimal;

import com.shoppproduct.dream_shops.model.Cart;

public interface ICartService {
    
    Cart getCart(int id);
    void clearCart(int id);
    BigDecimal getTotalPrice(int id);
    int initializeNewCart();

}
