package com.smartorders.productservice.service;

import com.smartorders.productservice.dto.request.CreateCategoryRequest;
import com.smartorders.productservice.dto.request.UpdateCategoryRequest;
import com.smartorders.productservice.dto.response.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryRequest request);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(String id);
    CategoryDto updateCategory(String id, UpdateCategoryRequest request);
    void deleteCategory(String id);
}
