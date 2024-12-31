package com.shoppproduct.dream_shops.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppproduct.dream_shops.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
    
    void deleteAllByCartId(int id);

}
