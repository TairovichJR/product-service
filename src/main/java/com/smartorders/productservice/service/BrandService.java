package com.smartorders.productservice.service;

import com.smartorders.productservice.dto.request.CreateBrandRequest;
import com.smartorders.productservice.dto.request.UpdateBrandRequest;
import com.smartorders.productservice.dto.response.BrandDto;

import java.util.List;

public interface BrandService {

    BrandDto createBrand(CreateBrandRequest request);
    List<BrandDto> getAllBrands();
    BrandDto getBrandById(String id);
    BrandDto updateBrand(String id, UpdateBrandRequest request);
    void deleteBrand(String id);

}

