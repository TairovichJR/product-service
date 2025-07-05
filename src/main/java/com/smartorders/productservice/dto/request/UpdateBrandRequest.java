package com.smartorders.productservice.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBrandRequest {

    @Length(min = 3, message = "Brand name must be at least 3 chars long")
    private String name;

    @Length(min = 3, message = "Brand description must be at least 10 chars long")
    private String description;

    @URL(message = "Logo URL must be a valid URL")
    private String logoUrl;

    @URL(message = "Website URL must be a valid URL")
    private String websiteUrl;

    private Boolean isActive;
}