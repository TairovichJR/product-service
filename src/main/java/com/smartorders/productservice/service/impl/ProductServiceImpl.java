package com.smartorders.productservice.service.impl;

import com.smartorders.productservice.dto.request.CreateProductRequest;
import com.smartorders.productservice.dto.request.UpdateProductRequest;
import com.smartorders.productservice.dto.response.ProductDto;
import com.smartorders.productservice.exception.*;
import com.smartorders.productservice.mapper.ProductAttributeMapper;
import com.smartorders.productservice.mapper.ProductImageMapper;
import com.smartorders.productservice.mapper.ProductMapper;
import com.smartorders.productservice.model.*;
import com.smartorders.productservice.repository.BrandRepository;
import com.smartorders.productservice.repository.CategoryRepository;
import com.smartorders.productservice.repository.ProductRepository;
import com.smartorders.productservice.repository.TagRepository;
import com.smartorders.productservice.service.ProductService;
import com.smartorders.productservice.util.SkuGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    

    @Override
    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products from the repository.");
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }


    @Override
    public ProductDto createProduct(CreateProductRequest request) {
        
        log.info("Validating the request object " + request);
        validateRequest(request);
        
        log.info("Request validation passed. Proceeding with product creation.");
        UUID brandId = UUID.fromString(request.getBrandId());
        UUID categoryId = UUID.fromString(request.getCategoryId());
        Set<UUID> tagIds =  request.getTagIds().stream().map(UUID::fromString).collect(Collectors.toSet());

        // Validate brandId, categoryId, and tagIds here (not shown in this snippet)
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> {
                    log.warn("Brand not found with ID: " + brandId);
                    return new BrandNotFoundException("Product creation failed. Brand id: " + brandId + " does not exist");
                });
        
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("Category not found with ID: " + categoryId);
                    return new CategoryNotFoundException("Product creation failed. Category id: " + categoryId + " does not exist");
                });

        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(tagIds));
        if (tags.size() != tagIds.size()) {
            Set<UUID> foundTagIds = tags.stream().map(Tag::getId).collect(Collectors.toSet());
            Set<UUID> invalidTagIds = new HashSet<>(tagIds);
            invalidTagIds.removeAll(foundTagIds);
            log.warn("Some tag IDs do not exist: " + invalidTagIds);
            throw new TagNotFoundException("Product creation failed. Tag IDs: " + invalidTagIds + " do not exist");
        }

        // Create and set up the product entity
        log.info("Creating product entity from request: " + request);
        Product product = ProductMapper.toEntity(request);

        product.setBrand(brand);
        product.setCategory(category);
        product.setTags(tags);
        product.setSku(SkuGenerator.generateSku(request.getTitle(), category.getCategoryName()));

        // Save the product to the database
        log.info("Saving product to the repository: " + product);
        Product savedProduct = productRepository.save(product);

        // Convert to DTO and return
        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto getProductById(String id) {
        validateUuid("Product ID", id, null);

        log.info("Fetching product by ID: {}", id);
        Product product = productRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return new InvalidRequestException("Product with ID: " + id + " does not exist");
                });

        return ProductMapper.toDto(product);
    }

    @Override
    public ProductDto updateProduct(String id, UpdateProductRequest request) {
        validateUuid("Product ID", id, null);

        log.info("Validating the update request for product ID: {}", id);
        if (request == null) {
            log.error("UpdateProductRequest is null for product ID: {}", id);
            throw new InvalidRequestException("UpdateProductRequest must not be null");
        }

        Product product = productRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return new InvalidRequestException("Product with ID: " + id + " does not exist");
                });

        if (request.getBrandId() != null) {
            validateUuid("Brand ID", request.getBrandId(), null);
            UUID brandId = UUID.fromString(request.getBrandId());
            Brand brand = brandRepository.findById(brandId)
                    .orElseThrow(() -> {
                        log.warn("Brand not found with ID: {}", brandId);
                        return new BrandNotFoundException("Brand with ID: " + brandId + " does not exist");
                    });
            product.setBrand(brand);
        }

        if (request.getCategoryId() != null) {
            validateUuid("Category ID", request.getCategoryId(), null);
            UUID categoryId = UUID.fromString(request.getCategoryId());
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> {
                        log.warn("Category not found with ID: {}", categoryId);
                        return new CategoryNotFoundException("Category with ID: " + categoryId + " does not exist");
                    });
            product.setCategory(category);
        }

        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            Set<UUID> tagIds = request.getTagIds().stream()
                    .map(UUID::fromString)
                    .collect(Collectors.toSet());
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(tagIds));
            if (tags.size() != tagIds.size()) {
                Set<UUID> foundTagIds = tags.stream().map(Tag::getId).collect(Collectors.toSet());
                Set<UUID> invalidTagIds = new HashSet<>(tagIds);
                invalidTagIds.removeAll(foundTagIds);
                log.warn("Some tag IDs do not exist: " + invalidTagIds);
                throw new TagNotFoundException("Tag IDs: " + invalidTagIds + " do not exist");
            }


            product.setTags(tags);
        }

        if (request.getTitle() != null && request.getTitle().trim().isEmpty()){
            log.warn("Title is empty in the request for product ID: {}", id);
            throw new InvalidRequestException("Title must not be empty");

        }
        if (request.getDescription() != null && request.getDescription().trim().isEmpty()) {
            log.warn("Description is empty in the request for product ID: {}", id);
            throw new InvalidRequestException("Description must not be empty");
        }

        product.setBasePrice(request.getBasePrice() != null ? request.getBasePrice() : product.getBasePrice());
        product.setSalePrice(request.getSalePrice() != null ? request.getSalePrice() : product.getSalePrice());


        if (request.getProductStatus() != null) {
            try {
                product.setProductStatus(Status.valueOf(request.getProductStatus().trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                log.warn("Invalid product status value: {}", request.getProductStatus());
                throw new InvalidRequestException("Invalid product status: " + request.getProductStatus());
            }
        } else {
            product.setProductStatus(product.getProductStatus());
        }


        if (request.getTitle() != null || request.getCategoryId() != null) {
            String title = request.getTitle();
            String categoryName = product.getCategory().getCategoryName();
            log.info("Generating SKU for product with title: {} and category: {}", title, categoryName);
            product.setSku(SkuGenerator.generateSku(title, categoryName));
        }

        if (request.getAttributes() != null) {
            // Clear existing attributes while maintaining proper relationship cleanup
            product.getProductAttributes().clear();

            if (!request.getAttributes().isEmpty()) {
                request.getAttributes().stream()
                        .map(ProductAttributeMapper::toEntity)
                        .forEach(attribute -> {
                            attribute.setProduct(product);  // Set the owning side
                            product.getProductAttributes().add(attribute);  // Add to the collection
                        });
            }
        }

        if (request.getImages() != null) {
            product.getProductImages().clear();
            if (!request.getImages().isEmpty()){
                request.getImages().stream()
                        .map(ProductImageMapper::toEntity)
                        .forEach(image -> {
                            image.setProduct(product);  // Set the owning side
                            product.getProductImages().add(image);  // Add to the collection
                        });
            }
        }

        log.info("Saving updated product to the repository: {}", product);
        Product updatedProduct = productRepository.save(product);

        log.info("Product updated successfully: {}", updatedProduct);
        return ProductMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(String id) {

        validateUuid("Product Id", id, null);

        log.info("Deleting product with ID: {}", id);

        Product product = productRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return new ProductNotFoundException("Product with ID: " + id + " does not exist");
                });
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> searchProducts(String searchQuery, String categoryId, String brandId, Double minPrice, Double maxPrice, int page, int size) {

        log.info("Searching products with query: {}, categoryId: {}, brandId: {}, minPrice: {}, maxPrice: {}, page: {}, size: {}",
                searchQuery, categoryId, brandId, minPrice, maxPrice, page, size);


        if (page < 0 || size <= 0) {
            log.warn("Invalid pagination parameters: page={}, size={}. Returning empty product list.", page, size);
            throw new InvalidRequestException("Page must be >= 0 and size must be > 0");
        }

        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            log.warn("Invalid price range: minPrice={}, maxPrice={}. Returning empty product list.", minPrice, maxPrice);
            throw new InvalidRequestException("Min price must be less than or equal to max price");
        }

        if (categoryId != null) {
            validateUuid("Category ID", categoryId, null);
        }
        if (brandId != null) {
            validateUuid("Brand ID", brandId, null);
        }

        return productRepository.searchProducts(searchQuery, categoryId, brandId, minPrice, maxPrice, page, size)
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateRequest(CreateProductRequest request) {
        if (request == null) {
            log.error("CreateProductRequest is null");
            throw new InvalidRequestException("CreateProductRequest must not be null");
        }

        List<String> errors = new ArrayList<>();

        validateUuid("Brand ID", request.getBrandId(), errors);
        validateUuid("Category ID", request.getCategoryId(), errors);

        if (request.getTagIds() == null || request.getTagIds().isEmpty()) {
            log.warn("Tag IDs are null or empty in the request: {}", request);
            errors.add("Tag IDs must not be null or empty");
        } else {
            for (String tagId : request.getTagIds()) {
                log.info("Validating Tag ID: {}", tagId);
                validateUuid("Tag ID", tagId, errors);
            }
        }

        if (!errors.isEmpty()) {
            throw new InvalidRequestException(String.join(", ", errors));
        }
    }

    /**
     * Validates a UUID string. If errors is null, throws an exception on error.
     * If errors is provided, adds error messages to the list instead of throwing.
     */
    private void validateUuid(String fieldName, String value, List<String> errors) {
        if (value == null || value.trim().isEmpty()) {
            log.warn("{} is null or empty{}", fieldName, errors == null ? "" : " in the request");
            String msg = fieldName + " must not be null or empty";

            if (errors == null) throw new InvalidRequestException(msg);
            errors.add(msg);
            return;
        }

        String trimmedValue = value.trim();
        if (!trimmedValue.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            log.warn("Invalid UUID format for {}: {}", fieldName, trimmedValue);
            String msg = "Invalid UUID format for " + fieldName + ". Please provide a valid UUID in the format: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
            if (errors == null) throw new InvalidRequestException(msg);
            errors.add(msg);
            return;
        }

        try {
            if (errors != null) log.info("Parsing UUID for {}: {}", fieldName, trimmedValue);
            UUID.fromString(trimmedValue);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid UUID format for {}: {}", fieldName, trimmedValue);
            String msg = "Invalid UUID format for " + fieldName + ". Please provide a valid UUID in the format: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
            if (errors == null) throw new InvalidRequestException(msg);
            errors.add(msg);
        }
    }
}
