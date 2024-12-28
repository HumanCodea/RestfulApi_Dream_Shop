package com.shoppproduct.dream_shops.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppproduct.dream_shops.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer>{
    
}
