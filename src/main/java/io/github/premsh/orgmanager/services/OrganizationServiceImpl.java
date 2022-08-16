package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.organization.CreateOrganizationDto;
import io.github.premsh.orgmanager.dto.organization.OrganizationDto;
import io.github.premsh.orgmanager.dto.organization.OrganizationsDto;
import io.github.premsh.orgmanager.dto.organization.UpdateOrganizationDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Organization;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService{
    @Autowired
    OrganizationRepo organizationRepo;
    @Override
    public ResponseEntity<OrganizationsDto> getAllOrganizations() {
        return new ResponseEntity<>(new OrganizationsDto(organizationRepo.findAll()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrganizationDto> getOrganizationById(Long id) {
        return new ResponseEntity<>(
                new OrganizationDto(organizationRepo.findById(id).orElseThrow(()->new EntityNotFoundException(String.format("Organization with id %d not found", id)))),HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CreatedDto> createOrganization(CreateOrganizationDto orgDto) {
        Organization newOrg = organizationRepo.save(orgDto.get());
        return new ResponseEntity<>(
                new CreatedDto(
                        String.format("New Organization successfully created, orgId : %d",newOrg.getId())
                        ,String.valueOf(newOrg.getId())
                ),HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<UpdatedDto> updateOrganization(UpdateOrganizationDto orgDto, Long orgId) {
        Organization subjectOrg = organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException(String.format("Organization with Id : %d not found", orgId)));
        orgDto.getUpdates(subjectOrg);
        organizationRepo.save(subjectOrg);
        return new ResponseEntity<>(
                new UpdatedDto(
                        String.format("Organization with Id : %d updated successfuly",orgId),
                        String.valueOf(orgId)
                ), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<DeletedDto> deleteOrganization(Long id) {
        if(organizationRepo.existsById(id)) organizationRepo.deleteById(id);
        return new ResponseEntity<>(
            new DeletedDto(String.format("Deleted Organization with id : %d", id), String.valueOf(id)),
            HttpStatus.ACCEPTED
        );
    }

    @Override
    public ResponseEntity<DeletedDto> searchOrganization(Long id) {
        return null;
    }
}
