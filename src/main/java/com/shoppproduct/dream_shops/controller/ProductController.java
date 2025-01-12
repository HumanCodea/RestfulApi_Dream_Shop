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

import com.shoppproduct.dream_shops.exception.AlreadyExistsException;
import com.shoppproduct.dream_shops.exception.ProductNotFoundException;
import com.shoppproduct.dream_shops.model.Product;
import com.shoppproduct.dream_shops.service.product.IProductService;
import com.shoppproduct.dream_shops.utils.dto.ProductDTO;
import com.shoppproduct.dream_shops.utils.request.AddProductRequest;
import com.shoppproduct.dream_shops.utils.request.UpdateProductRequest;
import com.shoppproduct.dream_shops.utils.response.ApiResponse;

@RestController
@RequestMapping(path = "${api.prefix}/products")
public class ProductController {
    
    @Autowired
    private IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct(){

        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDTO> productDTOs = productService.getConvertProduct(products);
            return ResponseEntity.ok(new ApiResponse("Get all product success", productDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get all product failed!",HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest addProductRequest){

        try {
            Product product2 = productService.addProduct(addProductRequest);
            return ResponseEntity.ok(new ApiResponse("Add product success!", product2));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/byId/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable int productId){
        try {
            Product product = productService.getProductById(productId);
            ProductDTO productDTO = productService.convertToDTO(product);
            return ResponseEntity.ok(new ApiResponse("Find product success!", productDTO));
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

    @GetMapping("/product/byCategoryName/{categoryName}")
    public ResponseEntity<ApiResponse> getProductsByCategoryName(@PathVariable String categoryName){

        try {
            List<Product> products = productService.getProductsByCategory(categoryName);
            List<ProductDTO> productDTOs = productService.getConvertProduct(products);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null)); 
            }
            return ResponseEntity.ok(new ApiResponse("Find product success", productDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/byBrand/{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand){

        try {
            List<Product> products = productService.getProductsByBrand(brand);
            List<ProductDTO> productDTOs = productService.getConvertProduct(products);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null)); 
            }
            return ResponseEntity.ok(new ApiResponse("Find product success", productDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/byName/{productName}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String productName){

        try {
            List<Product> products = productService.getProductsByName(productName);
            List<ProductDTO> productDTOs = productService.getConvertProduct(products);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null)); 
            }
            return ResponseEntity.ok(new ApiResponse("Find product success", productDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brand){

        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brand);
            List<ProductDTO> productDTOs = productService.getConvertProduct(products);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null)); 
            }
            return ResponseEntity.ok(new ApiResponse("Find product success", productDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String productName, @RequestParam String brand){

        try {
            List<Product> products = productService.getProductsByBrandAndName(productName, brand);
            List<ProductDTO> productDTOs = productService.getConvertProduct(products);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null)); 
            }
            return ResponseEntity.ok(new ApiResponse("Find product success", productDTOs));
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
