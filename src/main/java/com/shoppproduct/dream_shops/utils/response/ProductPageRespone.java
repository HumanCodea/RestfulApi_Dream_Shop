package com.shoppproduct.dream_shops.utils.response;

import java.util.List;

import com.shoppproduct.dream_shops.utils.dto.ProductDTO;

public record ProductPageRespone(List<ProductDTO> productDTOs,
                                Integer pageNumber,
                                Integer pageSize,
                                long totalElements,
                                int totalPages,
                                boolean isLast) {
    
}
