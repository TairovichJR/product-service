package com.smartorders.productservice.controller;

import com.smartorders.productservice.dto.request.CreateTagRequest;
import com.smartorders.productservice.dto.request.UpdateTagRequest;
import com.smartorders.productservice.dto.response.TagDto;
import com.smartorders.productservice.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
@Slf4j
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags(){

        log.info("Fetching all tags");
        List<TagDto> tags = tagService.getAllTags();
        log.debug("Fetched {} tags", tags.size());
        return ResponseEntity.status(HttpStatus.OK)
                .body(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable String id){

        log.info("Fetching tag with id: {}", id);
        TagDto tag = tagService.getTagById(id);
        log.debug("Fetched tag: {}", tag);
        return ResponseEntity.status(HttpStatus.OK)
                .body(tag);
    }

    @PostMapping
    public ResponseEntity<TagDto> createTag(@RequestBody CreateTagRequest request){

        log.info("Creating new tag with request: {}", request);
        TagDto createdTag = tagService.createNewTag(request);
        log.debug("Created tag: {}", createdTag);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdTag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(@RequestBody UpdateTagRequest request,
                                            @PathVariable String id){
        log.info("Updating tag with id: {} and request: {}", id, request);
        TagDto updatedTag = tagService.updateTag(id, request);
        log.debug("Updated tag: {}", updatedTag);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedTag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable String id){

        log.info("Deleting tag with id: {}", id);
        tagService.deleteTag(id);
        log.debug("Deleted tag with id: {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Tag with ID: " + id + " has been deleted");
    }
}