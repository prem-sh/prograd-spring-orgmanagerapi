package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.constants.Permissions;
import io.github.premsh.orgmanager.dto.AuthDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.tag.CreateTagDto;
import io.github.premsh.orgmanager.dto.tag.TagDto;
import io.github.premsh.orgmanager.dto.tag.SimpleTagsDto;
import io.github.premsh.orgmanager.dto.tag.UpdateTagDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Asset;
import io.github.premsh.orgmanager.models.Organization;
import io.github.premsh.orgmanager.models.Tag;
import io.github.premsh.orgmanager.models.User;
import io.github.premsh.orgmanager.repository.AssetsRepo;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import io.github.premsh.orgmanager.repository.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService{
    @Autowired  TagRepo tagRepo;
    @Autowired  AssetsRepo assetsRepo;
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;

    @Override
    public ResponseEntity<TagDto> getTagById(Long orgId, Long id) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.TAG_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(
                new TagDto(
                        tagRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Tag Not found"))
                ), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<SimpleTagsDto> getAllTags(Long orgId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.TAG_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(
                new SimpleTagsDto(tagRepo.findAll(orgId)),HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<SimpleTagsDto> filterTags(Long orgId, String searchText) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.TAG_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(
                new SimpleTagsDto(tagRepo.filter(orgId, searchText)), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CreatedDto> createTag(Long orgId, CreateTagDto dto) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.TAG_CREATE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Set<String> tags = new HashSet<String>();
        if(dto.getTags() == null && dto.getName() ==null ) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        if(dto.getName() != null) tags.add(dto.getName());
        if(dto.getTags() != null) tags.addAll(dto.getTags());
        List<Long> status = bulkAddTags(orgId, tags, auth.getUserId());
        return new ResponseEntity<>(new CreatedDto("Add Tag(s) request processed",status.toString()), HttpStatus.CREATED);
    }

    @Override
    public List<Long> bulkAddTags(Long orgId, Set<String> tags, Long createdBy){
        return tags.stream().map((t)->{
            Organization org = organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found"));
            if(!tagRepo.existByName(orgId, t)){
                Tag tag = new Tag();
                tag.setCreatedBy(createdBy);
                tag.setUpdatedBy(createdBy);
                tag.setOrganization(org);
                tag.setTagName(t);
                Tag newTag = tagRepo.save(tag);
                return newTag.getId();
            }else return null;
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<UpdatedDto> updateTag(Long orgId, UpdateTagDto dto) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.TAG_UPDATE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        if(dto.getFrom().isEmpty() || dto.getTo().isEmpty()) return new ResponseEntity<>(new UpdatedDto("Need both From and To fields to process, no operations carried out"), HttpStatus.BAD_REQUEST);
        if(dto.getFrom().size() != dto.getTo().size()) return new ResponseEntity<>(new UpdatedDto("Lists of From and To must with same size, no operations carried out"), HttpStatus.BAD_REQUEST);
        List<Long> status = bulkUpdateTags(orgId, dto.getFrom(), dto.getTo(), auth.getUserId());
        return new ResponseEntity<>(new UpdatedDto("Tag(s) updated successfully", status.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public List<Long> bulkUpdateTags(Long orgId, List<String> from, List<String> to, Long updatedBy){

        if(from.size() != to.size()) return new ArrayList<>();
        List<Long> status = new ArrayList<>();
        for (int i = 0; i<from.size(); i++){
            Tag tag = (tagRepo.findByName(orgId, from.get(i)).orElseThrow(()->new EntityNotFoundException("Tag not found")));
            tag.setUpdatedBy(updatedBy);
            tag.setTagName(to.get(i));
            tagRepo.save(tag);
            status.add(tag.getId());
        }
        return status;
    }

    @Override
    public Tag facilitateTag(Long orgId, String name, Long usrId, Organization org) {
        Optional<Tag> op = tagRepo.findByName(orgId, name);
        Tag ret;
        if(op.isPresent()){
            ret = (Tag) op.get();
        }else {
            Tag tag = new Tag();
            tag.setCreatedBy(usrId);
            tag.setUpdatedBy(usrId);
            tag.setOrganization(org);
            tag.setTagName(name);
            ret = tagRepo.save(tag);
        }
        return ret;
    }

    @Override
    public ResponseEntity<DeletedDto> deleteTag(Long orgId, Long id) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.TAG_DELETE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Tag tag = tagRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Tag not found"));
        Set<Asset> assets = assetsRepo.findAllByTag(orgId, tag.getId());
        for (Asset i: assets) {
            i.removeTag(tag);
        }
        tagRepo.delete(tag);
        return new ResponseEntity<>(new DeletedDto("Tag deleted successfully",String.valueOf(id)), HttpStatus.ACCEPTED);
    }
}
