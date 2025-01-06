package com.shoppproduct.dream_shops.repostitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppproduct.dream_shops.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>{

    List<Orders> findByUserId(Long userId);

}
