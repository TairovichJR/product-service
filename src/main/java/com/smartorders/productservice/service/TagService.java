package com.smartorders.productservice.service;

import com.smartorders.productservice.dto.request.CreateTagRequest;
import com.smartorders.productservice.dto.request.UpdateTagRequest;
import com.smartorders.productservice.dto.response.TagDto;

import java.util.List;

public interface TagService {

    TagDto createNewTag(CreateTagRequest request);
    TagDto getTagById(String id);
    List<TagDto> getAllTags();
    TagDto updateTag(String id, UpdateTagRequest request);
    void deleteTag(String id);
}
