package com.smartorders.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryRequest {

    @NotBlank(message = "Category name is required")
    @Length(min = 3, message = "Category name must be at least 3 chars long")
    private String categoryName;

    @NotBlank(message = "Category description is required")
    @Length(min = 3, message = "Category description must be at least 10 chars long")
    private String description;

    @NotBlank(message = "Active status is required")
    private Boolean isActive;

    private String parentCategoryId;
}