package com.shoppproduct.dream_shops.service.cart.Imp;

import java.math.BigDecimal;

import com.shoppproduct.dream_shops.auth.model.User;
import com.shoppproduct.dream_shops.model.Cart;
import com.shoppproduct.dream_shops.utils.dto.CartDTO;

public interface ICartService {
    
    Cart getCart(int id);
    void clearCart(int id);
    BigDecimal getTotalPrice(int id);
    Cart initializeNewCart(User user);
    Cart getCartByUserId(Long userId);
    CartDTO convertCartToDTO(Cart cart);
    
}
