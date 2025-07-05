package com.smartorders.productservice.mapper;

import com.smartorders.productservice.dto.response.BrandDto;
import com.smartorders.productservice.model.Brand;

public class BrandMapper {

    private BrandMapper(){}

    public static BrandDto toDto(Brand brand) {
        if (brand == null) {
            return null;
        }
        return BrandDto.builder()
                .id(brand.getId())
                .brandName(brand.getBrandName())
                .description(brand.getDescription())
                .brandCode(brand.getBrandCode())
                .logoUrl(brand.getLogoUrl())
                .websiteUrl(brand.getWebsiteUrl())
                .isActive(brand.getIsActive())
                .build();
    }

    public static Brand toEntity(BrandDto dto) {
        if (dto == null) {
            return null;
        }
        return Brand.builder()
                .id(dto.getId())
                .brandName(dto.getBrandName())
                .description(dto.getDescription())
                .brandCode(dto.getBrandCode())
                .logoUrl(dto.getLogoUrl())
                .websiteUrl(dto.getWebsiteUrl())
                .isActive(dto.isActive())
                .build();
    }
}
