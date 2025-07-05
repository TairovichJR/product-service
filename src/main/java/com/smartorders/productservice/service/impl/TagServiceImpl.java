package com.smartorders.productservice.service.impl;

import com.smartorders.productservice.dto.request.CreateTagRequest;
import com.smartorders.productservice.dto.request.UpdateTagRequest;
import com.smartorders.productservice.dto.response.TagDto;
import com.smartorders.productservice.exception.InvalidRequestException;
import com.smartorders.productservice.exception.TagNotFoundException;
import com.smartorders.productservice.mapper.TagMapper;
import com.smartorders.productservice.model.Tag;
import com.smartorders.productservice.repository.TagRepository;
import com.smartorders.productservice.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.smartorders.productservice.mapper.TagMapper.toDto;
import static com.smartorders.productservice.util.UuidValidator.validateUuid;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public TagDto createNewTag(CreateTagRequest request) {

        log.info("Validating the request object " + request);
        if (request == null){
            log.warn("Tag request object was missing or null ");
            throw new InvalidRequestException("Update Tag Request cannot be missing");
        }

        Tag tag = Tag.builder()
                .tagName(request.getName())
                .description(request.getDescription())
                .isActive(request.getIsActive())
                .build();
        log.info("Saving tag to the repository: " + tag);
        Tag savedTag = tagRepository.save(tag);
        return toDto(savedTag);
    }

    @Override
    public TagDto getTagById(String id) {

        log.info("Validating the tag id: " + id );
        validateUuid(id);

        log.info("Fetching tag by ID: {}", id);
        Tag tag = tagRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new TagNotFoundException("Tag with id " + id + " does not exist"));
        return toDto(tag);
    }

    @Override
    public List<TagDto> getAllTags() {
        log.info("Fetching all tags from the repository");
        return tagRepository.findAll().stream()
                .map(TagMapper::toDto)
                .toList();
    }

    @Override
    public TagDto updateTag(String id, UpdateTagRequest request) {

        log.info("Validating the tag id: " + id );
        validateUuid(id);

        log.info("Validating the request object " + request);
        if (request == null){
            throw new InvalidRequestException("Update Tag Request cannot be missing");
        }


        log.info("Fetching tag by ID: {}", id);
        Tag tag = tagRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new TagNotFoundException("Tag with id " + id + " does not exist"));

        log.info("Updating the existing tag");
        tag.setTagName(request.getName() != null ? request.getName() : tag.getTagName());
        tag.setDescription(request.getDescription() != null ? request.getDescription() : tag.getDescription());
        tag.setIsActive(request.getIsActive() != null ? request.getIsActive() : tag.getIsActive());

        log.info("Saving tag to the repository: " + tag);
        return toDto(tagRepository.save(tag));
    }

    @Override
    public void deleteTag(String id) {
        log.info("Validating the tag id: " + id );
        validateUuid(id);

        log.info("Fetching tag by ID: {}", id);
        tagRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new TagNotFoundException("Tag with id " + id + " does not exist"));
        log.info("Deleting tag by ID: {}", id);
        tagRepository.deleteById(UUID.fromString(id));
    }
}
