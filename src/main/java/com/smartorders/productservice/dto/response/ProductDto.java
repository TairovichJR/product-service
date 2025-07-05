package com.smartorders.productservice.dto.response;

import com.smartorders.productservice.model.Status;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private UUID id;
    private String title;
    private String description;
    private String sku;
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private Status productStatus;
    private BrandDto brand;        // Full brand object
    private CategoryDto category;   // Full category object
    private Set<TagDto> tags = new HashSet<>();      // Full tag objects
    private List<ProductAttributeDto> attributes = new ArrayList<>();
    private List<ProductImageDto> images = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}