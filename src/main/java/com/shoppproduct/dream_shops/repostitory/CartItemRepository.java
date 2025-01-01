package com.shoppproduct.dream_shops.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppproduct.dream_shops.model.CartItem;

import jakarta.transaction.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
    
    @Transactional
    void deleteAllByCartId(int id);

}
