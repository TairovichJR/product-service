package com.smartorders.productservice.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageRequest {
    private String imageUrl;
    private String altText;
    private String thumbnailUrl;
    private String caption;
    private boolean isPrimary;
}