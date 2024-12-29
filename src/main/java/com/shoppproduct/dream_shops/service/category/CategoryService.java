package com.shoppproduct.dream_shops.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppproduct.dream_shops.exception.AlreadyExistsException;
import com.shoppproduct.dream_shops.exception.CategoryNotFoundException;
import com.shoppproduct.dream_shops.model.Category;
import com.shoppproduct.dream_shops.repostitory.CategoryRepository;

@Service
public class CategoryService implements ICategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException("Not found category!"));
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByNameCategory(categoryName);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
        .filter(c -> !categoryRepository.existsByNameCategory(c.getNameCategory())) //Lọc ra những category mới để thêm vào cơ sở dữ liệu.
        .map(categoryRepository :: save)
        .orElseThrow(() -> new AlreadyExistsException(category.getNameCategory() + "already exists"));
    }

    @Override
    public Category updateCategory(Category category, int categoryId) {
        return Optional.ofNullable(getCategoryById(categoryId)).map(oldCategory -> {
                oldCategory.setNameCategory(category.getNameCategory());
                return categoryRepository.save(oldCategory);
            }) .orElseThrow(() -> new CategoryNotFoundException("Not found category!"));
    }

    @Override
    public void deleteCategoryById(int categoryId) {
        categoryRepository.findById(categoryId).ifPresentOrElse(
            categoryRepository :: delete, () -> {
                throw new CategoryNotFoundException("Not found category!");
            });
    }
    
}
