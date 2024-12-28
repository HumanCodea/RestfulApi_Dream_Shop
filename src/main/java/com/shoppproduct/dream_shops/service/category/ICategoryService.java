package com.shoppproduct.dream_shops.service.category;

import java.util.List;

import com.shoppproduct.dream_shops.model.Category;

public interface ICategoryService {

    Category getCategoryById(int categoryId);
    Category getCategoryByName(String categoryName);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, int categoryId);
    void deleteCategoryById(int categoryId);

}
