package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.tag.CreateTagDto;
import io.github.premsh.orgmanager.dto.tag.TagDto;
import io.github.premsh.orgmanager.dto.tag.TagsDto;
import io.github.premsh.orgmanager.dto.tag.UpdateTagDto;
import io.github.premsh.orgmanager.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("org/{orgId}/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable Long orgId, @PathVariable Long id) {
        return tagService.getTagById(orgId, id);
    }

    @GetMapping("/all")
    public ResponseEntity<TagsDto> getAllTags(@PathVariable Long orgId) {
        return tagService.getAllTags(orgId);
    }

    @GetMapping("/filter/{searchText}")
    public ResponseEntity<TagsDto> filterTags(@PathVariable Long orgId, @PathVariable String searchText) {
        return tagService.filterTags(orgId, searchText);
    }

    @PostMapping
    public ResponseEntity<CreatedDto> createTag(@PathVariable Long orgId, @Valid @RequestBody CreateTagDto dto) {
        return tagService.createTag(orgId, dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatedDto> updateTag(@PathVariable Long orgId, @Valid @RequestBody UpdateTagDto dto, @PathVariable Long id) {
        return tagService.updateTag(orgId, dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletedDto> deleteTag(@PathVariable Long orgId, @PathVariable Long id) {
        return tagService.deleteTag(orgId, id);
    }
}
