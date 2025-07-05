package com.smartorders.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTagRequest {
    @Length(min = 3, message = "Name must be at least 3 chars long")
    private String name;
    @Length(min = 10, message = "Description must be at least 10 chars long")
    private String description;
    private Boolean isActive;
}