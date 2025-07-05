package com.smartorders.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTagRequest {

    @NotBlank(message = "Tag name is required")
    @Length(min = 3, message = "Tag name must be at least 3 chars long")
    private String name;

    @NotBlank(message = "Tag description is required")
    @Length(min = 10, message = "Tag description must be at least 10 chars long")
    private String description;

    @NotBlank(message = "Active status is required")
    private Boolean isActive;
}