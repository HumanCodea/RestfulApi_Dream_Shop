package com.shoppproduct.dream_shops.dto;

import java.math.BigDecimal;
import java.util.List;

import com.shoppproduct.dream_shops.model.Category;

import lombok.Data;

@Data
public class ProductDTO {

    private int productId;
    private String productBrand;
    private String productName;
    private BigDecimal productPrices;
    private int inventory;
    private String productDescription;
    private Category category;
    private List<ImageDTO> images;
    
}

