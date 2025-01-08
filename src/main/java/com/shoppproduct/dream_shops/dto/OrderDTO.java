package com.shoppproduct.dream_shops.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.shoppproduct.dream_shops.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderDTO {
    
    private Long id;
    private Long userId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private List<OrderItemDTO> items;

}
