package com.smartorders.productservice.controller;

import com.smartorders.productservice.dto.request.CreateProductRequest;
import com.smartorders.productservice.dto.request.UpdateProductRequest;
import com.smartorders.productservice.dto.response.ProductDto;
import com.smartorders.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {

        List<ProductDto> productDtos = productService.getAllProducts();
        log.info("Fetched {} products from the service", productDtos.size());
        return ResponseEntity.status(HttpStatus.OK).body(productDtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid CreateProductRequest request) {

        log.info("Received request to create product: {}", request);
        ProductDto productDto = productService.createProduct(request);
        log.info("Product created successfully: {}", productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {

        log.info("Fetching product with ID: {}", id);
        ProductDto productDto = productService.getProductById(id);
        log.info("Fetched product: {}", productDto);
        return ResponseEntity.status(HttpStatus.OK).body(productDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @RequestBody @Valid UpdateProductRequest request) {

        log.info("Received request to update product with ID: {}. Request: {}", id, request);
        ProductDto updatedProduct = productService.updateProduct(id, request);
        log.info("Product updated successfully: {}", updatedProduct);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {

        log.info("Received request to delete product with ID: {}", id);
        productService.deleteProduct(id);
        log.info("Product with ID: {} deleted successfully", id);
        return ResponseEntity.status(HttpStatus.OK).body("Product with ID: " + id + " has been deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts (
            @RequestParam (required = false) String searchQuery,
            @RequestBody (required = false) String categoryId,
            @RequestParam (required = false) String brandId,
            @RequestParam (required = false) Double minPrice,
            @RequestParam (required = false) Double maxPrice,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size) {

        log.info("Searching products with query: {}, categoryId: {}, brandId: {}, minPrice: {}, maxPrice: {}, page: {}, size: {}",
                searchQuery, categoryId, brandId, minPrice, maxPrice, page, size);
        List<ProductDto> productDtos = productService.searchProducts(searchQuery, categoryId, brandId, minPrice, maxPrice, page, size);
        log.info("Found {} products matching the search criteria", productDtos.size());
        return ResponseEntity.status(HttpStatus.OK).body(productDtos);
    }

}
