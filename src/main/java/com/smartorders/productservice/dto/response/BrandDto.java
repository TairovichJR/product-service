package com.smartorders.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandDto {
    private UUID id;
    private String brandName;
    private String description; // Optional description for the brand
    private String brandCode; // Unique code for the brand
    private String logoUrl; // URL to the brand's logo image
    private String websiteUrl; // URL to the brand's official website
//    private LocalDateTime createdAt; // Creation timestamp in ISO 8601 format
//    private LocalDateTime updatedAt; // Last update timestamp in ISO 8601 format
    private boolean isActive; // Indicates if the brand is currently active
}