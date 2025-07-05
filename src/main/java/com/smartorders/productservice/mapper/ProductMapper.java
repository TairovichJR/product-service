package com.smartorders.productservice.mapper;

import com.smartorders.productservice.dto.request.CreateProductRequest;
import com.smartorders.productservice.dto.response.ProductDto;
import com.smartorders.productservice.model.Product;
import com.smartorders.productservice.model.ProductAttribute;
import com.smartorders.productservice.model.ProductImage;
import com.smartorders.productservice.model.Status;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setSku(product.getSku());
        dto.setBasePrice(product.getBasePrice());
        dto.setSalePrice(product.getSalePrice());
        dto.setProductStatus(product.getProductStatus());
        dto.setBrand(product.getBrand() != null ? BrandMapper.toDto(product.getBrand()) : null);
        dto.setCategory(product.getCategory() != null ? CategoryMapper.toDto(product.getCategory()) : null);
        dto.setTags(product.getTags() != null ? product.getTags().stream()
                .map(TagMapper::toDto)
                .collect(Collectors.toSet()) : new HashSet<>());
        dto.setAttributes(product.getProductAttributes() != null ? product.getProductAttributes().stream()
                .map(ProductAttributeMapper::toDto)
                .collect(Collectors.toList()) : new ArrayList<>());
        dto.setImages(product.getProductImages() != null ? product.getProductImages().stream()
                .map(ProductImageMapper::toDto)
                .collect(Collectors.toList()) : new ArrayList<>());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setProductStatus(product.getProductStatus());
        return dto;
    }

    public static Product toEntity(CreateProductRequest request) {
        if (request == null) {
            return null;
        }

        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setBasePrice(request.getBasePrice());
        product.setSalePrice(request.getSalePrice());
        product.setProductStatus(Status.valueOf(request.getProductStatus().toUpperCase()));

        // Handle attributes
        if (request.getAttributes() != null) {
            request.getAttributes().stream()
                    .map(ProductAttributeMapper::toEntity)
                    .forEach(attribute -> {
                        attribute.setProduct(product);
                        product.getProductAttributes().add(attribute);
                    });
        }

        // Handle images
        if (request.getImages() != null) {
            request.getImages().stream()
                    .map(ProductImageMapper::toEntity)
                    .forEach(image -> {
                        image.setProduct(product);
                        product.getProductImages().add(image);
                    });
        }

        return product;
    }
}