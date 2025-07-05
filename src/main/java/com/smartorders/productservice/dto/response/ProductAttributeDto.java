package com.smartorders.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttributeDto {
    private UUID id;
    private String name; // Name of the attribute (e.g., "Color", "Size")
    private String value; // Value of the attribute (e.g., "Red", "Large")
    private int sortOrder; // Order in which the attribute should be displayed
    private String type; // Type of the attribute (e.g., "color", "size", "material")
    private String unit; // Unit of measurement for the attribute (e.g., "cm", "kg", "pcs")
    private String description; // Optional description for the attribute
}
