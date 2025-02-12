package com.shoppproduct.dream_shops.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppproduct.dream_shops.exception.AlreadyExistsException;
import com.shoppproduct.dream_shops.exception.CategoryNotFoundException;
import com.shoppproduct.dream_shops.model.Category;
import com.shoppproduct.dream_shops.service.category.ICategoryService;
import com.shoppproduct.dream_shops.utils.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "${api.prefix}/categories")
@Tag(name = "Category Controller")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;
    
    @GetMapping("/all")
    @Operation(summary = "Get category", description = "Api to get all categories")
    public ResponseEntity<ApiResponse> getAllCategories(){

        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Get all success!", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get all failed!", HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    @Operation(summary = "Add category", description = "Api to create new category")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){

        try {
            Category category2 = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Add success!", category2));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/category/id/{categoryId}")
    @Operation(summary = "Get category", description = "Api to get category by id")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable int categoryId){

        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Find success!", category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/category/name/{nameCategory}")
    @Operation(summary = "Get category", description = "Api to get category by name")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String nameCategory){

        try {
            Category category = categoryService.getCategoryByName(nameCategory);
            return ResponseEntity.ok(new ApiResponse("Find success!", category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{categoryId}")
    @Operation(summary = "Delete category", description = "Api to delete category by id")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable int categoryId){

        try {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Delete success!", null));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{categoryId}")
    @Operation(summary = "Update category", description = "Api to update category by id")
    public ResponseEntity<ApiResponse> updateCategoryById(@PathVariable int categoryId, @RequestBody Category category){

        try {
            Category category2 = categoryService.updateCategory(category, categoryId);
            return ResponseEntity.ok(new ApiResponse("Update success!", category2));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

}
