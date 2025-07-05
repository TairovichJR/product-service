package com.smartorders.productservice.service.impl;

import com.smartorders.productservice.dto.request.CreateBrandRequest;
import com.smartorders.productservice.dto.request.UpdateBrandRequest;
import com.smartorders.productservice.dto.response.BrandDto;
import com.smartorders.productservice.exception.BrandNotFoundException;
import com.smartorders.productservice.exception.InvalidRequestException;
import com.smartorders.productservice.mapper.BrandMapper;
import com.smartorders.productservice.model.Brand;
import com.smartorders.productservice.repository.BrandRepository;
import com.smartorders.productservice.service.BrandService;
import com.smartorders.productservice.util.BrandCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.smartorders.productservice.mapper.BrandMapper.toDto;
import static com.smartorders.productservice.util.UuidValidator.validateUuid;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public BrandDto createBrand(CreateBrandRequest request) {

        if (request == null){
            throw new InvalidRequestException("Create brand request cannot be missing");
        }

        String brandCode;
        do {
            brandCode = BrandCodeGenerator.generateBrandCode();
        }while (brandRepository.existsByBrandCode(brandCode));

        Brand brand = Brand.builder()
                .brandName(request.getName())
                .description(request.getDescription())
                .isActive(request.getIsActive())
                .brandCode(brandCode)
                .logoUrl(request.getLogoUrl())
                .websiteUrl(request.getWebsiteUrl())
                .build();

        Brand savedBrand = brandRepository.save(brand);

        return toDto(savedBrand);
    }


    @Override
    public List<BrandDto> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(BrandMapper:: toDto)
                .toList();
    }

    @Override
    public BrandDto getBrandById(String id) {
        validateUuid(id);
        Brand brand = brandRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new BrandNotFoundException("Brand with id " + id + " does not exist"));
        return toDto(brand);
    }

    @Override
    public BrandDto updateBrand(String id, UpdateBrandRequest request) {
        validateUuid(id);

        if (request == null){
            throw new InvalidRequestException("Create brand request cannot be missing");
        }

        Brand brand = brandRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new BrandNotFoundException("Brand with id " + id + " does not exist"));

        brand.setBrandName(request.getName() != null ? request.getName() : brand.getBrandName());
        brand.setDescription(request.getDescription() != null ? request.getDescription() : brand.getDescription());
        brand.setLogoUrl(request.getLogoUrl() != null ? request.getLogoUrl() : brand.getLogoUrl());
        brand.setWebsiteUrl(request.getWebsiteUrl() != null ? request.getWebsiteUrl() :  brand.getWebsiteUrl());
        brand.setIsActive(request.getIsActive() != null ? request.getIsActive() : brand.getIsActive());

        return toDto(brandRepository.save(brand));
    }

    @Override
    public void deleteBrand(String id) {
        validateUuid(id);

        brandRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new BrandNotFoundException("Brand with id " + id + " does not exist"));
        brandRepository.deleteById(UUID.fromString(id));
    }
}