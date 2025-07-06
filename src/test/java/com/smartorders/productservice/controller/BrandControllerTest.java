package com.smartorders.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartorders.productservice.dto.response.BrandDto;
import com.smartorders.productservice.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BrandController.class)
public class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BrandService brandService;

    @Test
    public void getBrandById_shouldReturnBrand_WhenValidId() throws Exception {
        //Arrange
        String brandId = String.valueOf(UUID.randomUUID());
        BrandDto brandDto = BrandDto.builder()
                .id(UUID.fromString(brandId))
                .brandName("Test Brand Name")
                .description("Test Brand Description")
                .build();
       when(brandService.getBrandById(brandId)).thenReturn(brandDto);

        mockMvc.perform(get("/api/v1/brands/{id}", brandId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(String.valueOf(brandDto.getId())))
                .andExpect(jsonPath("$.brandName").value(brandDto.getBrandName()))
                .andExpect(jsonPath("$.description").value(brandDto.getDescription()));

        verify(brandService).getBrandById(brandId);

    }

}
