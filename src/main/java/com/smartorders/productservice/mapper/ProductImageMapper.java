package com.smartorders.productservice.mapper;

import com.smartorders.productservice.dto.request.ProductImageRequest;
import com.smartorders.productservice.dto.response.ProductImageDto;
import com.smartorders.productservice.model.ProductImage;

public class ProductImageMapper {
    public static ProductImageDto toDto(ProductImage productImage) {

        if (productImage == null) {
            return null;
        }
        return ProductImageDto.builder()
                .id(productImage.getId())
                .imageUrl(productImage.getImageUrl())
                .altText(productImage.getAltText())
                .thumbnailUrl(productImage.getThumbnailUrl())
                .caption(productImage.getCaption())
                .isPrimary(productImage.isPrimary())
                .build();
    }

    public static ProductImage toEntity(ProductImageRequest request) {
        if (request == null) {
            return null;
        }
        return ProductImage.builder()
                .imageUrl(request.getImageUrl())
                .altText(request.getAltText())
                .thumbnailUrl(request.getThumbnailUrl())
                .caption(request.getCaption())
                .isPrimary(request.isPrimary())
                .build();
    }
}