package com.shoppproduct.dream_shops.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppproduct.dream_shops.auth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    Boolean existsByEmail(String email);

    User findByEmail(String email);

}
