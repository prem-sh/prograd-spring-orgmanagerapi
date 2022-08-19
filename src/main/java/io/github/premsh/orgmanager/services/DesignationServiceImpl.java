package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.designation.CreateDesignationDto;
import io.github.premsh.orgmanager.dto.designation.DesignationDto;
import io.github.premsh.orgmanager.dto.designation.DesignationsDto;
import io.github.premsh.orgmanager.dto.designation.UpdateDesignationDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Designation;
import io.github.premsh.orgmanager.repository.DesignationRepo;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DesignationServiceImpl implements DesignationService{
    @Autowired
    private DesignationRepo designationRepo;
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;

    @Override
    public ResponseEntity<DesignationsDto> getAllDesignation(Long orgId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new DesignationsDto(designationRepo.findAll(orgId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DesignationDto> getDesignation(Long orgId, Long desId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new DesignationDto(designationRepo.findById(orgId, desId).orElseThrow(()->new EntityNotFoundException("Designation not found"))), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedDto> createDesignation(Long orgId, CreateDesignationDto desDto) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Designation newDep = new Designation();
        newDep.setDesignationName(desDto.getName());
        newDep.setOrganization(
                organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found"))
        );
        designationRepo.save(newDep);
        return new ResponseEntity<>(new CreatedDto("Designation created successfully", String.valueOf(newDep.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateDesignation(Long orgId, UpdateDesignationDto desDto, Long desId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Designation subjectDep = designationRepo.findById(orgId, desId).orElseThrow(()->new EntityNotFoundException("Designation not found"));
        subjectDep.setDesignationName(desDto.getName());
        designationRepo.save(subjectDep);
        return new ResponseEntity<>(new UpdatedDto("Designation name updated successfully", desId.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DeletedDto> deleteDesignation(Long orgId, Long desId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        if(designationRepo.existsById(orgId, desId)) designationRepo.deleteById(desId);
        else throw new EntityNotFoundException("Department not found");
        return new ResponseEntity<>(new DeletedDto("Department deleted successfully", desId.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DesignationsDto> filterDesignation(String searchText, Long orgId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new DesignationsDto(designationRepo.filter(orgId, searchText)), HttpStatus.OK);
    }
}
