package com.smartorders.productservice.mapper;

import com.smartorders.productservice.dto.response.CategoryDto;
import com.smartorders.productservice.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    // Main mapper
    public static CategoryDto toDto(Category category) {
        if (category == null) return null;

        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .isActive(category.getIsActive())
                .parentCategory(toMinimalDto(category.getParentCategory()))
                .subCategories(category.getSubCategories() != null
                        ? category.getSubCategories().stream()
                        .map(CategoryMapper::toMinimalDto)
                        .collect(Collectors.toList())
                        : null)
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    // Lightweight DTO to avoid infinite loop
    public static CategoryDto toMinimalDto(Category category) {
        if (category == null) return null;

        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .isActive(category.getIsActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    // If needed (not used often)
    public static Category toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) return null;

        return Category.builder()
                .id(categoryDto.getId())
                .categoryName(categoryDto.getCategoryName())
                .description(categoryDto.getDescription())
                .isActive(categoryDto.isActive())
                .createdAt(categoryDto.getCreatedAt())
                .updatedAt(categoryDto.getUpdatedAt())
                .build();
    }
}
