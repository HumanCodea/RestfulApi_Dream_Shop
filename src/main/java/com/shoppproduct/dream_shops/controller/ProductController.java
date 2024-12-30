package com.shoppproduct.dream_shops.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppproduct.dream_shops.exception.ProductNotFoundException;
import com.shoppproduct.dream_shops.model.Product;
import com.shoppproduct.dream_shops.request.AddProductRequest;
import com.shoppproduct.dream_shops.request.UpdateProductRequest;
import com.shoppproduct.dream_shops.response.ApiResponse;
import com.shoppproduct.dream_shops.service.product.IProductService;

@RestController
@RequestMapping(path = "${api.prefix}/products")
public class ProductController {
    
    @Autowired
    private IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct(){

        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("Get all product success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get all product failed!",HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest addProductRequest){

        try {
            Product product2 = productService.addProduct(addProductRequest);
            return ResponseEntity.ok(new ApiResponse("Add product success!", product2));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable int productId){
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Find product success!", product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable int productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product success!", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProductById(@RequestBody UpdateProductRequest updateProductRequest, @PathVariable int productId){

        try {
            Product product = productService.updateProduct(updateProductRequest, productId);
            return ResponseEntity.ok(new ApiResponse("Update product success!", product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/{categoryName}")
    public ResponseEntity<ApiResponse> getProductsByCategoryName(@PathVariable String categoryName){

        try {
            List<Product> products = productService.getProductsByCategory(categoryName);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null)); 
            }
            return ResponseEntity.ok(new ApiResponse("Find product success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand){

        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null)); 
            }
            return ResponseEntity.ok(new ApiResponse("Find product success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/{productName}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String productName){

        try {
            List<Product> products = productService.getProductsByName(productName);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null)); 
            }
            return ResponseEntity.ok(new ApiResponse("Find product success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brand){

        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null)); 
            }
            return ResponseEntity.ok(new ApiResponse("Find product success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String productName, @RequestParam String brand){

        try {
            List<Product> products = productService.getProductsByBrandAndName(productName, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null)); 
            }
            return ResponseEntity.ok(new ApiResponse("Find product success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/countProduct")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String productName, @RequestParam String brand){

        try {
            int productCount = productService.countProductsByBrandAndName(brand, productName);
            return ResponseEntity.ok(new ApiResponse("Find product success", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

}
