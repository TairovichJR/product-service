package com.smartorders.productservice.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCategoryRequest {

    @Length(min = 3, message = "Category name must be at least 3 chars long")
    private String categoryName;

    @Length(min = 3, message = "Category description must be at least 10 chars long")
    private String description;

    private Boolean isActive;
    private String parentCategoryId;
}