package com.shoppproduct.dream_shops.service.product;

import java.util.List;

import com.shoppproduct.dream_shops.dto.ProductDTO;
import com.shoppproduct.dream_shops.model.Product;
import com.shoppproduct.dream_shops.request.AddProductRequest;
import com.shoppproduct.dream_shops.request.UpdateProductRequest;

public interface IProductService {
    
    Product addProduct(AddProductRequest product);
    Product getProductById(int productId);
    void deleteProductById(int productId);
    Product updateProduct(UpdateProductRequest product, int productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String productName);
    List<Product> getProductsByBrandAndName(String productName, String brand);
    int countProductsByBrandAndName(String brand, String productName);
    public ProductDTO convertToDTO(Product product);
    public List<ProductDTO> getConvertProduct(List<Product> products);
}
