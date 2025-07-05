package com.smartorders.productservice.mapper;

import com.smartorders.productservice.dto.response.TagDto;
import com.smartorders.productservice.model.Tag;

public class TagMapper {

    public static TagDto toDto(Tag tag) {
        if (tag == null) {
            return null;
        }
        return TagDto.builder()
                .id(tag.getId())
                .tagName(tag.getTagName())
                .description(tag.getDescription())
                .isActive(tag.getIsActive())
                .build();
    }

    public static Tag toEntity(TagDto tagDto) {
        if (tagDto == null) {
            return null;
        }
        return Tag.builder()
                .id(tagDto.getId())
                .tagName(tagDto.getTagName())
                .description(tagDto.getDescription())
                .isActive(tagDto.isActive())
                .build();
    }
}