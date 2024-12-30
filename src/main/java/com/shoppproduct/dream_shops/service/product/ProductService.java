package com.shoppproduct.dream_shops.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppproduct.dream_shops.exception.ProductNotFoundException;
import com.shoppproduct.dream_shops.model.Category;
import com.shoppproduct.dream_shops.model.Product;
import com.shoppproduct.dream_shops.repostitory.CategoryRepository;
import com.shoppproduct.dream_shops.repostitory.ProductRepository;
import com.shoppproduct.dream_shops.request.AddProductRequest;
import com.shoppproduct.dream_shops.request.UpdateProductRequest;

@Service
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest product) {

        Category category = Optional.ofNullable(categoryRepository.findByNameCategory(product.getCategory().getNameCategory()))
                .orElseGet(() -> {
                    Category newCategory = new Category(product.getCategory().getNameCategory());
                    return categoryRepository.save(newCategory);
                });

        product.setCategory(category);
        return productRepository.save(createProduct(product, category));     
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

    // Sử dụng .map() khi bạn muốn xử lý các giá trị bên trong Optional một cách ngắn gọn, 
    // đặc biệt là khi cần thực hiện nhiều bước xử lý liên tiếp
    @Override
    public Product updateProduct(UpdateProductRequest product, int productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, product)) 
                .map(productRepository :: save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));

    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request){

        existingProduct.setProductName(request.getProductName());
        existingProduct.setProductBrand(request.getProductBrand());
        existingProduct.setProductPrices(request.getProductPrices());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setProductDescription(request.getProductDescription());
        
        Category category = categoryRepository.findByNameCategory(request.getCategory().getNameCategory());
        existingProduct.setCategory(category);

        return existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryNameCategory(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByProductBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameCategoryAndProductBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String productName) {
        return productRepository.findByProductName(productName);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String productName, String brand) {
        return productRepository.findByProductBrandAndProductName(brand, productName);
    }

    @Override
    public int countProductsByBrandAndName(String brand, String productName) {
       return productRepository.countByProductBrandAndProductName(brand,productName);
    }
    
}
