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
public class ProductImageDto {

    private UUID id; // Unique identifier for the image
    private String imageUrl; // URL of the product image
    private String altText; // Alternative text for the image, useful for accessibility
    private String thumbnailUrl; // URL of the thumbnail version of the image
    private String caption;
    private boolean isPrimary; // Indicates if this image is the primary image for the product
}
