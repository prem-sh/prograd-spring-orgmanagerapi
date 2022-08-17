package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.tag.CreateTagDto;
import io.github.premsh.orgmanager.dto.tag.TagDto;
import io.github.premsh.orgmanager.dto.tag.TagsDto;
import io.github.premsh.orgmanager.dto.tag.UpdateTagDto;
import org.springframework.http.ResponseEntity;

public interface TagService {
    ResponseEntity<TagDto> getTagById(Long orgId, Long id);
    ResponseEntity<TagsDto> getAllTags(Long orgId);
    ResponseEntity<TagsDto> filterTags(Long orgId, String searchText);
    ResponseEntity<CreatedDto> createTag(Long orgId, CreateTagDto dto);
    ResponseEntity<UpdatedDto> updateTag(Long orgId, UpdateTagDto dto, Long id);
    ResponseEntity<DeletedDto> deleteTag(Long orgId, Long id);
}
