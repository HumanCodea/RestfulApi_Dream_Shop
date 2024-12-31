package com.shoppproduct.dream_shops.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppproduct.dream_shops.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{
    
}
