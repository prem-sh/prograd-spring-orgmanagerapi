package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.tag.CreateTagDto;
import io.github.premsh.orgmanager.dto.tag.TagDto;
import io.github.premsh.orgmanager.dto.tag.SimpleTagsDto;
import io.github.premsh.orgmanager.dto.tag.UpdateTagDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Organization;
import io.github.premsh.orgmanager.models.Tag;
import io.github.premsh.orgmanager.models.User;
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
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;

    @Override
    public ResponseEntity<TagDto> getTagById(Long orgId, Long id) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(
                new TagDto(
                        tagRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Tag Not found"))
                ), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<SimpleTagsDto> getAllTags(Long orgId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(
                new SimpleTagsDto(tagRepo.findAll(orgId)),HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<SimpleTagsDto> filterTags(Long orgId, String searchText) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(
                new SimpleTagsDto(tagRepo.filter(orgId, searchText)), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CreatedDto> createTag(Long orgId, CreateTagDto dto) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Set<String> tags = new HashSet<String>();
        if(dto.getTags() == null && dto.getName() ==null ) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        if(dto.getName() != null) tags.add(dto.getName());
        if(dto.getTags() != null) tags.addAll(dto.getTags());
        List<Long> status = bulkAddTags(orgId, tags);
        return new ResponseEntity<>(new CreatedDto("Add Tag(s) request processed",status.toString()), HttpStatus.CREATED);
    }

    @Override
    public List<Long> bulkAddTags(Long orgId, Set<String> tags){
        return tags.stream().map((t)->{
            Organization org = organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found"));
            User usr = principalService.getUser();
            if(!tagRepo.existByName(orgId, t)){
                Tag tag = new Tag();
                tag.setCreatedBy(usr);
                tag.setUpdatedBy(usr);
                tag.setOrganization(org);
                tag.setTag(t);
                Tag newTag = tagRepo.save(tag);
                return newTag.getId();
            }else return null;
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<UpdatedDto> updateTag(Long orgId, UpdateTagDto dto) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        if(dto.getFrom().isEmpty() || dto.getTo().isEmpty()) return new ResponseEntity<>(new UpdatedDto("Need both From and To fields to process, no operations carried out"), HttpStatus.BAD_REQUEST);
        if(dto.getFrom().size() != dto.getTo().size()) return new ResponseEntity<>(new UpdatedDto("Lists of From and To must with same size, no operations carried out"), HttpStatus.BAD_REQUEST);
        List<Long> status = bulkUpdateTags(orgId, dto.getFrom(), dto.getTo());
        return new ResponseEntity<>(new UpdatedDto("Tag(s) updated successfully", status.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public List<Long> bulkUpdateTags(Long orgId, List<String> from, List<String> to){

        if(from.size() != to.size()) return new ArrayList<>();
        User usr = principalService.getUser();
        List<Long> status = new ArrayList<>();
        for (int i = 0; i<from.size(); i++){
            Tag tag = (tagRepo.findByName(orgId, from.get(i)).orElseThrow(()->new EntityNotFoundException("Tag not found")));
            tag.setUpdatedBy(usr);
            tag.setTag(to.get(i));
            tagRepo.save(tag);
            status.add(tag.getId());
        }
        return status;
    }

    @Override
    public Tag facilitateTag(Long orgId, String name, User usr, Organization org) {
        Optional<Tag> op = tagRepo.findByName(orgId, name);
        Tag ret;
        if(op.isPresent()){
            ret = (Tag) op.get();
        }else {
            Tag tag = new Tag();
            tag.setCreatedBy(usr);
            tag.setUpdatedBy(usr);
            tag.setOrganization(org);
            tag.setTag(name);
            ret = tagRepo.save(tag);
        }
        return ret;
    }

    @Override
    public ResponseEntity<DeletedDto> deleteTag(Long orgId, Long id) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        if (!tagRepo.existsById(orgId, id)) throw new EntityNotFoundException("Tag not found");
        Tag tag = tagRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Tag not found"));
        tag.setDeletedBy(principalService.getUser());
        tag.setDeletedAt(new Date());
        tag.setIsDeleted(true);
        tagRepo.save(tag);
        return new ResponseEntity<>(new DeletedDto("Employee deleted successfully",String.valueOf(tag.getId())), HttpStatus.ACCEPTED);
    }
}
