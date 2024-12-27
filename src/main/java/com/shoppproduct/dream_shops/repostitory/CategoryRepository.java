package com.shoppproduct.dream_shops.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppproduct.dream_shops.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
    Category findByName(String name);

}
