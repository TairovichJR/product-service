package com.smartorders.productservice.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttributeRequest {
    private String name;
    private String value;
    private int sortOrder;
    private String type; // e.g., "text", "number", "date", etc.
    private String unit; // e.g., "cm", "kg", "pcs"
    private String description; // Optional description for the attribute
}