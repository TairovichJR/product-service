package com.smartorders.productservice.mapper;

import com.smartorders.productservice.dto.request.ProductAttributeRequest;
import com.smartorders.productservice.dto.response.ProductAttributeDto;
import com.smartorders.productservice.model.ProductAttribute;

public class ProductAttributeMapper {


    public static ProductAttributeDto toDto(ProductAttribute productAttribute) {
        if (productAttribute == null) {
            return null;
        }
        return ProductAttributeDto.builder()
                .id(productAttribute.getId())
                .name(productAttribute.getName())
                .value(productAttribute.getValue())
                .type(productAttribute.getType())
                .unit(productAttribute.getUnit())
                .description(productAttribute.getDescription())
                .build();
    }

    public static ProductAttribute toEntity(ProductAttributeRequest request) {
        if (request == null) {
            return null;
        }
        return ProductAttribute.builder()
                .name(request.getName())
                .value(request.getValue())
                .type(request.getType())
                .unit(request.getUnit())
                .description(request.getDescription())
                .build();
    }
}
