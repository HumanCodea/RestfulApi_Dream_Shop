package com.shoppproduct.dream_shops.utils.request;

import java.math.BigDecimal;

import com.shoppproduct.dream_shops.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
    private int productId;
    private String productName;
    private String productBrand;
    private BigDecimal productPrices;
    private int inventory;
    private String productDescription;
    private Category category;
}
