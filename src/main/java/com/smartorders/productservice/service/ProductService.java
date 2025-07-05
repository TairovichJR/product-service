package com.smartorders.productservice.service;

import com.smartorders.productservice.dto.request.CreateProductRequest;
import com.smartorders.productservice.dto.request.UpdateProductRequest;
import com.smartorders.productservice.dto.response.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto createProduct(CreateProductRequest request);

    ProductDto getProductById(String id);

    ProductDto updateProduct(String id, UpdateProductRequest request);

    void deleteProduct(String id);

    List<ProductDto> searchProducts(String searchQuery, String categoryId, String brandId, Double minPrice, Double maxPrice, int page, int size);
}
