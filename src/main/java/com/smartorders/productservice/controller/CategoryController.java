package com.smartorders.productservice.controller;

import com.smartorders.productservice.dto.request.CreateCategoryRequest;
import com.smartorders.productservice.dto.request.UpdateCategoryRequest;
import com.smartorders.productservice.dto.response.CategoryDto;
import com.smartorders.productservice.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){

        log.info("Received request to get all categories");
        List<CategoryDto> categories = categoryService.getAllCategories();
        log.debug("Fetched {} categories", categories.size());
        return ResponseEntity.status(HttpStatus.OK)
                .body(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String id){

        log.info("Received request to get category by id: {}", id);
        CategoryDto category = categoryService.getCategoryById(id);
        log.debug("Fetched category: {}", category);
        return ResponseEntity.status(HttpStatus.OK)
                .body(category);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CreateCategoryRequest request){

        log.info("Received request to create category: {}", request);
        CategoryDto createdCategory = categoryService.createCategory(request);
        log.debug("Created category: {}", createdCategory);
        return ResponseEntity.status(HttpStatus.OK)
                .body(createdCategory);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String id,
                                                      @RequestBody UpdateCategoryRequest request){
        log.info("Received request to update category with id: {} and data: {}", id, request);
        CategoryDto updatedCategory = categoryService.updateCategory(id, request);
        log.debug("Updated category: {}", updatedCategory);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable String id){

        log.info("Received request to delete category with id: {}", id);
        // Assuming the service actually deletes the category
        categoryService.deleteCategory(id);
        log.debug("Deleted category with id: {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Category with ID: " + id + " has been deleted");
    }
}