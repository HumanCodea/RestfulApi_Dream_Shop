package com.shoppproduct.dream_shops.repostitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppproduct.dream_shops.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>{
    
    List<Image> findByProductProductId(int productId);

}
