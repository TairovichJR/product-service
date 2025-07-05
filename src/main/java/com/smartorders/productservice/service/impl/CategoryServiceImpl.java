package com.smartorders.productservice.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.smartorders.productservice.dto.request.CreateCategoryRequest;
import com.smartorders.productservice.dto.request.UpdateCategoryRequest;
import com.smartorders.productservice.dto.response.CategoryDto;
import com.smartorders.productservice.exception.CategoryNotFoundException;
import com.smartorders.productservice.exception.InvalidRequestException;
import com.smartorders.productservice.mapper.CategoryMapper;
import com.smartorders.productservice.model.Category;
import com.smartorders.productservice.repository.CategoryRepository;
import com.smartorders.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import static com.smartorders.productservice.mapper.CategoryMapper.toDto;
import static com.smartorders.productservice.util.UuidValidator.validateUuid;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CreateCategoryRequest request) {

        if (request == null){
            throw new InvalidRequestException("Category request body cannot be missing");
        }

        Category parentCategory = null;

        // Check if parentCategoryId is provided
        if (request.getParentCategoryId() != null && !request.getParentCategoryId().trim().isEmpty()) {

            validateUuid(request.getParentCategoryId());

            UUID parentId = UUID.fromString(request.getParentCategoryId());

            parentCategory = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new CategoryNotFoundException(
                            "Parent category with ID " + parentId + " not found"));

        }

        // Create new category
        Category category = Category.builder()
                .categoryName(request.getCategoryName())
                .description(request.getDescription())
                .isActive(request.getIsActive())
                .parentCategory(parentCategory) // null if no parent (root category)
                .build();

        Category savedCategory = categoryRepository.save(category);
        return toDto(savedCategory);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(String id) {
        validateUuid(id);

        UUID categoryId = UUID.fromString(id);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));
        return toDto(category);
    }

    @Override
    public CategoryDto updateCategory(String id, UpdateCategoryRequest request) {

        validateUuid(id);

        UUID categoryId = UUID.fromString(id);
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));

        // Handle parent category validation (same logic as create)
        Category parentCategory = null;
        if (request.getParentCategoryId() != null && !request.getParentCategoryId().trim().isEmpty()) {
            UUID parentId = UUID.fromString(request.getParentCategoryId());

            if (parentId.equals(categoryId)) {
                throw new InvalidRequestException("A category cannot be its own parent.");
            }

            parentCategory = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new CategoryNotFoundException(
                            "Parent category with ID " + parentId + " not found"));
        }

        // Update category fields
        existingCategory.setCategoryName(request.getCategoryName() != null ? request.getCategoryName() : existingCategory.getCategoryName());
        existingCategory.setDescription(request.getDescription() != null ? request.getDescription() : existingCategory.getDescription());
        existingCategory.setIsActive(request.getIsActive() != null ? request.getIsActive() : existingCategory.getIsActive());
        existingCategory.setParentCategory(parentCategory != null ? parentCategory : existingCategory.getParentCategory());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return toDto(updatedCategory);
    }

    @Override
    public void deleteCategory(String id) {
        UUID categoryId = UUID.fromString(id);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));
        categoryRepository.delete(category);
    }
}