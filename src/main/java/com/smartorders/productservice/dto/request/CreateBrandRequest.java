package com.smartorders.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBrandRequest {

    @NotBlank(message = "Brand name is required")
    @Length(min = 3, message = "Brand name must be at least 3 chars long")
    private String name;

    @NotBlank(message = "Brand description is required")
    @Length(min = 3, message = "Brand description must be at least 10 chars long")
    private String description;

    @NotBlank(message = "Brand logo URL is required")
    @URL(message = "Logo URL must be a valid URL")
    private String logoUrl;

    @NotBlank(message = "Brand website URL is required")
    @URL(message = "Website URL must be a valid URL")
    private String websiteUrl;

    @NotNull(message = "Active status is required")
    private Boolean isActive;
}