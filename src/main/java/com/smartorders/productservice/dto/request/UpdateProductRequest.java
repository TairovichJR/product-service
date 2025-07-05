package com.smartorders.productservice.dto.request;

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
public class UpdateProductRequest {

    private String title;

    private String description;

    @PositiveOrZero(message = "Base Price must not be negative")
    private BigDecimal basePrice;

    @PositiveOrZero(message = "Sale Price must not be negative")
    private BigDecimal salePrice;

    private String productStatus;

    private String brandId;
    private String categoryId;
    private Set<String> tagIds;
    private List<ProductAttributeRequest> attributes;
    private List<ProductImageRequest> images;
}