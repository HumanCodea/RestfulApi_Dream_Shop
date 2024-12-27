package com.shoppproduct.dream_shops.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppproduct.dream_shops.exception.ProductNotFoundException;
import com.shoppproduct.dream_shops.model.Category;
import com.shoppproduct.dream_shops.model.Product;
import com.shoppproduct.dream_shops.repostitory.ProductRepository;
import com.shoppproduct.dream_shops.request.AddProductRequest;

@Service
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(AddProductRequest product) {
        return null;     
    }

    private Product createProduct(AddProductRequest request, Category category){

        return new Product(
            request.getProductBrand(),
            request.getProductName(), 
            request.getProductPrices(),
            request.getInventory(), 
            request.getProductDescription(), 
            category);

    }

    @Override
    public Product getProductById(int productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(int productId) {
       productRepository.findById(productId)
            .ifPresentOrElse(productRepository::delete,
                    () -> { throw new ProductNotFoundException("Product not found!");});
    }

    @Override
    public void updateProduct(Product product, int productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String productName) {
        return productRepository.findByProductName(productName);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String productName, String brand) {
        return productRepository.findByBrandAndProductName(brand, productName);
    }

    @Override
    public int countProductsByBrandAndName(String brand, String productName) {
       return productRepository.countByBrandAndProductName(brand,productName);
    }
    
}
