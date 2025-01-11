package com.shoppproduct.dream_shops.service.cart.Imp;

import com.shoppproduct.dream_shops.model.CartItem;

public interface ICartItemService {
    
    void addItemToCart(int cartId, int productId, int quantity);
    void removeItemFromCart(int cartId, int productId);
    void updateItemQuantity(int cartId, int productId, int quantity);
    CartItem getCartItem(int cartId, int productId);
}
