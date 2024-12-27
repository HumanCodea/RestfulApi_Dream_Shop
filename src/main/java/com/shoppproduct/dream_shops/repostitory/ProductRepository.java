package com.shoppproduct.dream_shops.repostitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppproduct.dream_shops.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{
    
    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByProductName(String productName);

    List<Product> findByBrandAndProductName(String brand, String productName);

    int countByBrandAndProductName(String brand, String productName);
}
