package com.shoppproduct.dream_shops.request;

import java.math.BigDecimal;

import com.shoppproduct.dream_shops.model.Category;

import lombok.Data;

@Data
public class UpdateProductRequest {
    private int productId;
    private String productName;
    private String productBrand;
    private BigDecimal productPrices;
    private int inventory;
    private String productDescription;
    private Category category;
}
