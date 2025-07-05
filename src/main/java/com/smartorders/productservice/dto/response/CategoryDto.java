package com.smartorders.productservice.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private UUID id;
    private String categoryName;
    private String description;
    private boolean isActive;

    // For parent reference (without circular reference)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CategoryDto parentCategory;

    // For tree structure
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryDto> subCategories;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}