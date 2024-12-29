package com.shoppproduct.dream_shops.repostitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppproduct.dream_shops.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{
    
    List<Product> findByCategoryNameCategory(String category);

    List<Product> findByProductBrand(String productBrand);

    List<Product> findByCategoryNameCategoryAndProductBrand(String category, String productBrand);

    List<Product> findByProductName(String productName);

    List<Product> findByProductBrandAndProductName(String productBrand, String productName);

    int countByProductBrandAndProductName(String productBrand, String productName);

}
