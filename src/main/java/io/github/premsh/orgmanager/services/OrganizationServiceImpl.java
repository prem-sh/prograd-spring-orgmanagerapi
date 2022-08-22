package io.github.premsh.orgmanager.services;

import com.sun.jdi.InternalException;
import io.github.premsh.orgmanager.constants.Defaults;
import io.github.premsh.orgmanager.constants.RoleConstants;
import io.github.premsh.orgmanager.dto.organization.CreateOrganizationDto;
import io.github.premsh.orgmanager.dto.organization.OrganizationDto;
import io.github.premsh.orgmanager.dto.organization.OrganizationsDto;
import io.github.premsh.orgmanager.dto.organization.UpdateOrganizationDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityAlreadyExistException;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.*;
import io.github.premsh.orgmanager.repository.DepartmentRepo;
import io.github.premsh.orgmanager.repository.MemberProfileRepo;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import io.github.premsh.orgmanager.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService{
    @Autowired
    OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private MemberProfileRepo memberProfileRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DesignationService designationService;

    @Override
    public ResponseEntity<OrganizationsDto> getAllOrganizations() {
        User usr = principalService.getUser();

        if(usr == null) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        if (usr.getRoles().contains(RoleConstants.SUPERADMIN) || usr.getRoles().contains(RoleConstants.SUPPORT)){
            return new ResponseEntity<>(new OrganizationsDto(organizationRepo.findAll()), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new OrganizationsDto(
                    memberProfileRepo.findByUserId(usr.getId()).stream().map(MemberProfile::getOrganization).collect(Collectors.toList())
            ), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<OrganizationDto> getOrganizationById(Long id) {
        User usr = principalService.getUser();
        if(usr == null) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        if (usr.getRoles().contains(RoleConstants.SUPERADMIN) || usr.getRoles().contains(RoleConstants.SUPPORT)){
            return new ResponseEntity<>(
                    new OrganizationDto(organizationRepo.findById(id).orElseThrow(()->new EntityNotFoundException(String.format("Organization with id %d not found", id)))),HttpStatus.OK
            );
        }else{
            return new ResponseEntity<>(new OrganizationDto(
                    (memberProfileRepo.findByOrgIdUserId(id, usr.getId()).orElseThrow(()->new EntityNotFoundException("Organiztion not found"))).getOrganization()
            ), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<CreatedDto> createOrganization(CreateOrganizationDto orgDto) {
        User usr = principalService.getUser();
        if(usr == null) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);


        Organization newOrg = orgDto.get();
        if(organizationRepo.existsByEmail(newOrg.getEmail())){
            throw new EntityAlreadyExistException("Organization email already taken");
        }
        if(organizationRepo.existsByPhone(newOrg.getPhone())){
            throw new EntityAlreadyExistException("Organization Phone number already taken");
        }
        newOrg.setUpdatedBy(usr.getId());
        newOrg.setCreatedBy(usr.getId());
        Organization org = organizationRepo.save(newOrg);

        Department defaultDepartment = departmentService.createDefaultDepartment(org);
        Designation defaultDesignation = designationService.createDefaultDesignation(org);

        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setUser(usr);
        memberProfile.setRole(roleRepo.findByRoleName(Defaults.DEFAULT_ROLE).orElseThrow(()->new EntityNotFoundException("Internal error : Default Role not found")));
        memberProfile.setOrganization(org);
        memberProfile.setDepartment(defaultDepartment);
        memberProfile.setDesignation(defaultDesignation);
        memberProfile.setCreatedBy(usr.getId());
        memberProfile.setUpdatedBy(usr.getId());
        memberProfileRepo.save(memberProfile);

        return new ResponseEntity<>(
                new CreatedDto(
                        String.format("New Organization successfully created, orgId : %d",newOrg.getId())
                        ,String.valueOf(org.getId())
                ),HttpStatus.CREATED
        );
    }



    @Override
    public ResponseEntity<UpdatedDto> updateOrganization(UpdateOrganizationDto orgDto, Long orgId) {
        User usr = principalService.getUser();
        if(usr == null) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        if (! principalService.hasAuthority(orgId, List.of(
                RoleConstants.ADMIN
        ))) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Organization subjectOrg = (memberProfileRepo.findByOrgIdUserIdRoleId(orgId, usr.getId(), RoleConstants.ADMIN).orElseThrow(()->new EntityNotFoundException(String.format("Organization with Id : %d not found", orgId)))).getOrganization();
        orgDto.getUpdates(subjectOrg);
        subjectOrg.setUpdatedBy(usr.getId());
        organizationRepo.save(subjectOrg);
        return new ResponseEntity<>(
                new UpdatedDto(
                        String.format("Organization with Id : %d updated successfully",orgId),
                        String.valueOf(orgId)
                ), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<DeletedDto> deleteOrganization(Long id) {
        User usr = principalService.getUser();
        if(usr == null) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        if (! principalService.hasAuthority(id, List.of(
                RoleConstants.ADMIN
        ))) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Organization subjectOrg = organizationRepo.findByI(id).orElseThrow(()->new EntityNotFoundException(String.format("Organization with Id : %d not found", id)));
        organizationRepo.delete(subjectOrg);

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
