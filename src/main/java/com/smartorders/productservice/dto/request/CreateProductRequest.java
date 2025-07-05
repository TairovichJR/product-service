package com.smartorders.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {

    @NotBlank(message = "Product Title is required")
    private String title;
    @NotBlank(message = "Product Description is required")
    private String description;

    @NotNull(message = "Base Price is required")
    @PositiveOrZero(message = "Base Price must not be negative")
    private BigDecimal basePrice;
    @PositiveOrZero(message = "Sale Price must not be negative")
    private BigDecimal salePrice;

    @NotBlank(message = "Product Status is required")
    private String productStatus;

    private String brandId;
    private String categoryId;
    private Set<String> tagIds;

    private List<ProductAttributeRequest> attributes;
    private List<ProductImageRequest> images;
}