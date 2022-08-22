package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.tag.CreateTagDto;
import io.github.premsh.orgmanager.dto.tag.TagDto;
import io.github.premsh.orgmanager.dto.tag.SimpleTagsDto;
import io.github.premsh.orgmanager.dto.tag.UpdateTagDto;
import io.github.premsh.orgmanager.models.Organization;
import io.github.premsh.orgmanager.models.Tag;
import io.github.premsh.orgmanager.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface TagService {
    ResponseEntity<TagDto> getTagById(Long orgId, Long id);
    ResponseEntity<SimpleTagsDto> getAllTags(Long orgId);
    ResponseEntity<SimpleTagsDto> filterTags(Long orgId, String searchText);
    ResponseEntity<CreatedDto> createTag(Long orgId, CreateTagDto dto);
    ResponseEntity<UpdatedDto> updateTag(Long orgId, UpdateTagDto dto);
    ResponseEntity<DeletedDto> deleteTag(Long orgId, Long id);
    List<Long> bulkAddTags(Long orgId, Set<String> tags, Long createdBy);
    List<Long> bulkUpdateTags(Long orgId, List<String> from, List<String> to, Long updatedBy);
    Tag facilitateTag(Long orgId, String name, Long usrId, Organization org);
}
