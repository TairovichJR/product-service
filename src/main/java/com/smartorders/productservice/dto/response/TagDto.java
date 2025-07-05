package com.smartorders.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {
    private UUID id;
    private String tagName;
    private String description; // Optional description for the tag
    private boolean isActive; // Indicates if the tag is currently active
}