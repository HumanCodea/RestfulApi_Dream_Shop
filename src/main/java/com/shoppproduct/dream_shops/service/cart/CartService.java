package com.shoppproduct.dream_shops.service.cart;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppproduct.dream_shops.exception.CartNotFoundException;
import com.shoppproduct.dream_shops.model.Cart;
import com.shoppproduct.dream_shops.model.CartItem;
import com.shoppproduct.dream_shops.repostitory.CartItemRepository;
import com.shoppproduct.dream_shops.repostitory.CartRepository;

@Service
public class CartService implements ICartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(int id) {
        Cart cart = cartRepository.findById(id)
            .orElseThrow(() -> new CartNotFoundException("Not found cart by id = " + id));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(int id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(int id) {
        Cart cart = getCart(id);
        return cart.getCartItems()
                .stream()
                .map(CartItem :: getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal :: add); // tổng giá trị của tất cả các items trong cart
    }
    
}
