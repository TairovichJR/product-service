package com.smartorders.productservice.controller;

import com.smartorders.productservice.dto.request.CreateBrandRequest;
import com.smartorders.productservice.dto.request.UpdateBrandRequest;
import com.smartorders.productservice.dto.response.BrandDto;
import com.smartorders.productservice.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@Slf4j
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands(){

        log.info("Fetching all brands");
        List<BrandDto> brands = brandService.getAllBrands();
        log.debug("Fetched {} brands", brands.size());
        return ResponseEntity.status(HttpStatus.OK)
                .body(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable String id){

        log.info("Fetching brand with ID: {}", id);
        BrandDto brand = brandService.getBrandById(id);
        log.debug("Fetched brand: {}", brand);
        return ResponseEntity.status(HttpStatus.OK)
                .body(brand);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandDto> createBrand(@RequestBody @Valid CreateBrandRequest request){

        log.info("Creating new brand with request: {}", request);
        BrandDto createdBrand = brandService.createBrand(request);
        log.debug("Created brand: {}", createdBrand);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdBrand);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandDto> updateBrand(@RequestBody @Valid UpdateBrandRequest request,
                                                @PathVariable String id){
        log.info("Updating brand with ID: {} using request: {}", id, request);
        BrandDto updatedBrand = brandService.updateBrand(id, request);
        log.debug("Updated brand: {}", updatedBrand);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedBrand);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBrand(@PathVariable String id){

        log.info("Deleting brand with ID: {}", id);
        brandService.deleteBrand(id);
        log.debug("Deleted brand with ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Brand with ID: " + id + " has been deleted");
    }
}