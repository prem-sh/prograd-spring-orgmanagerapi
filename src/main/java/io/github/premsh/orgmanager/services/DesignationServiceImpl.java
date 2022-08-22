package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.constants.Defaults;
import io.github.premsh.orgmanager.constants.Permissions;
import io.github.premsh.orgmanager.dto.AuthDto;
import io.github.premsh.orgmanager.dto.designation.CreateDesignationDto;
import io.github.premsh.orgmanager.dto.designation.DesignationDto;
import io.github.premsh.orgmanager.dto.designation.DesignationsDto;
import io.github.premsh.orgmanager.dto.designation.UpdateDesignationDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityAlreadyExistException;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Department;
import io.github.premsh.orgmanager.models.Designation;
import io.github.premsh.orgmanager.models.MemberProfile;
import io.github.premsh.orgmanager.models.Organization;
import io.github.premsh.orgmanager.repository.DesignationRepo;
import io.github.premsh.orgmanager.repository.MemberProfileRepo;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationServiceImpl implements DesignationService{
    @Autowired
    private DesignationRepo designationRepo;
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private MemberProfileRepo memberProfileRepo;

    @Override
    public ResponseEntity<DesignationsDto> getAllDesignation(Long orgId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.DESIGNATION_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new DesignationsDto(designationRepo.findAll(orgId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DesignationDto> getDesignation(Long orgId, Long desId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.DESIGNATION_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new DesignationDto(designationRepo.findById(orgId, desId).orElseThrow(()->new EntityNotFoundException("Designation not found"))), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedDto> createDesignation(Long orgId, CreateDesignationDto desDto) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.DESIGNATION_CREATE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        if (designationRepo.existsByDesignationName(orgId,desDto.getName())){
            throw new EntityAlreadyExistException("Designation already exist");
        }

        Designation newDes = new Designation();
        newDes.setDesignationName(desDto.getName());
        newDes.setOrganization(
                organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found"))
        );
        newDes.setCreatedBy(auth.getUserId());
        newDes.setUpdatedBy(auth.getUserId());
        designationRepo.save(newDes);
        return new ResponseEntity<>(new CreatedDto("Designation created successfully", String.valueOf(newDes.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateDesignation(Long orgId, UpdateDesignationDto desDto, Long desId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.DESIGNATION_UPDATE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        if (designationRepo.existsByDesignationName(orgId,desDto.getName())){
            throw new EntityAlreadyExistException("Designation already exist");
        }

        Designation subjectDes = designationRepo.findById(orgId, desId).orElseThrow(()->new EntityNotFoundException("Designation not found"));
        subjectDes.setDesignationName(desDto.getName());
        subjectDes.setUpdatedBy(auth.getUserId());

        designationRepo.save(subjectDes);
        return new ResponseEntity<>(new UpdatedDto("Designation name updated successfully", desId.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DeletedDto> deleteDesignation(Long orgId, Long desId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.DESIGNATION_DELETE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Designation subjectDes = designationRepo.findById(orgId, desId).orElseThrow(()->new EntityNotFoundException("Department not found"));
        if (subjectDes.getDesignationName().equals(Defaults.DEFAULT_DESIGNATION)) new ResponseEntity<>(new DeletedDto("Default designation cannot be deleted", desId.toString()), HttpStatus.NOT_ACCEPTABLE);

        Designation defaultDesignation = designationRepo.findByName(orgId, Defaults.DEFAULT_DESIGNATION).orElseThrow(()->new EntityNotFoundException("Default Designation not found, Please Create a Designation with name 'DEFAULT'"));
        List<MemberProfile> memberProfiles = memberProfileRepo.findMemberByDesignationName(orgId, subjectDes.getDesignationName());

        for (MemberProfile m: memberProfiles) {
            m.setDesignation(defaultDesignation);
        }

        designationRepo.delete(subjectDes);
        return new ResponseEntity<>(new DeletedDto("Designation deleted successfully", desId.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DesignationsDto> filterDesignation(String searchText, Long orgId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.DESIGNATION_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new DesignationsDto(designationRepo.filter(orgId, searchText)), HttpStatus.OK);
    }

    @Override
    public Designation createDefaultDesignation(Organization org) {
        Designation newDes = new Designation();
        newDes.setDesignationName(Defaults.DEFAULT_DESIGNATION);
        newDes.setOrganization(org);
        newDes.setCreatedBy(org.getCreatedBy());
        newDes.setUpdatedBy(org.getUpdatedBy());
        return designationRepo.save(newDes);
    }
}
