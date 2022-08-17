package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.tag.CreateTagDto;
import io.github.premsh.orgmanager.dto.tag.TagDto;
import io.github.premsh.orgmanager.dto.tag.TagsDto;
import io.github.premsh.orgmanager.dto.tag.UpdateTagDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Tag;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import io.github.premsh.orgmanager.repository.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TagServiceImpl implements TagService{
    @Autowired  TagRepo tagRepo;
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;

    @Override
    public ResponseEntity<TagDto> getTagById(Long orgId, Long id) {
        return new ResponseEntity<>(
                new TagDto(
                        tagRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Tag Not found"))
                ), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<TagsDto> getAllTags(Long orgId) {
        return new ResponseEntity<>(
                new TagsDto(tagRepo.findAll(orgId)),HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<TagsDto> filterTags(Long orgId, String searchText) {
        return new ResponseEntity<>(
                new TagsDto(tagRepo.filter(orgId, searchText)), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CreatedDto> createTag(Long orgId, CreateTagDto dto) {
        Tag tag = new Tag();
        tag.setCreatedBy(principalService.getUser());
        tag.setUpdatedBy(principalService.getUser());
        tag.setOrganization(organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found")));
        tag.setTag(dto.getName());
        Tag newTag = tagRepo.save(tag);
        return new ResponseEntity<>(new CreatedDto("Employee created successfully",String.valueOf(newTag.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateTag(Long orgId, UpdateTagDto dto, Long id) {
        Tag tag = tagRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Tag not found"));
        tag.setUpdatedBy(principalService.getUser());
        tag.setTag(dto.getName());
        tagRepo.save(tag);
        return new ResponseEntity<>(new UpdatedDto("Employee updated successfully",String.valueOf(tag.getId())), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DeletedDto> deleteTag(Long orgId, Long id) {
        if (!tagRepo.existsById(orgId, id)) throw new EntityNotFoundException("Tag not found");
        Tag tag = tagRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Tag not found"));
        tag.setDeletedBy(principalService.getUser());
        tag.setDeletedAt(new Date());
        tag.setIsDeleted(true);
        tagRepo.save(tag);
        return new ResponseEntity<>(new DeletedDto("Employee deleted successfully",String.valueOf(tag.getId())), HttpStatus.ACCEPTED);
    }
}
