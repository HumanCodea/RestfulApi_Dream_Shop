package com.shoppproduct.dream_shops.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartDTO {
    private int id;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private List<CartItemDTO> cartItemDTOs;
}
