package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.constants.Defaults;
import io.github.premsh.orgmanager.constants.Permissions;
import io.github.premsh.orgmanager.dto.AuthDto;
import io.github.premsh.orgmanager.dto.department.CreateDepartmentDto;
import io.github.premsh.orgmanager.dto.department.DepartmentDto;
import io.github.premsh.orgmanager.dto.department.DepartmentsDto;
import io.github.premsh.orgmanager.dto.department.UpdateDepartmentDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityAlreadyExistException;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.*;
import io.github.premsh.orgmanager.repository.DepartmentRepo;
import io.github.premsh.orgmanager.repository.MemberProfileRepo;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import io.github.premsh.orgmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private MemberProfileRepo memberProfileRepo;
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;

    @Override
    public ResponseEntity<DepartmentsDto> getAllDepartment(Long orgId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.DEPARTMENT_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new DepartmentsDto(departmentRepo.findAll(orgId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DepartmentDto> getDepartment(Long orgId, Long depId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.DEPARTMENT_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new DepartmentDto(departmentRepo.findById(orgId, depId).orElseThrow(()->new EntityNotFoundException("Department not found"))), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedDto> createDepartment(Long orgId, CreateDepartmentDto depDto) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.DEPARTMENT_CREATE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        if (departmentRepo.existsByDepartmentName(orgId,depDto.getName())){
            throw new EntityAlreadyExistException("Department already exist");
        }

        Department newDep = new Department();
        newDep.setDepartmentName(depDto.getName());
        newDep.setOrganization(
                organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found"))
        );

        newDep.setCreatedBy(auth.getUserId());
        newDep.setUpdatedBy(auth.getUserId());

        departmentRepo.save(newDep);
        return new ResponseEntity<>(new CreatedDto("Department created successfully", String.valueOf(newDep.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateDepartment(Long orgId, UpdateDepartmentDto depDto, Long depId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.DEPARTMENT_UPDATE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        if (departmentRepo.existsByDepartmentName(orgId,depDto.getName())){
            throw new EntityAlreadyExistException("Department already exist");
        }
        Department subjectDep = departmentRepo.findById(orgId, depId).orElseThrow(()->new EntityNotFoundException("Department not found"));
        subjectDep.setDepartmentName(depDto.getName());
        subjectDep.setUpdatedBy(auth.getUserId());
        departmentRepo.save(subjectDep);
        return new ResponseEntity<>(new UpdatedDto("Department updated successfully", depId.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DeletedDto> deleteDepartment(Long orgId, Long depId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.DEPARTMENT_DELETE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Department subjectDep = departmentRepo.findById(orgId, depId).orElseThrow(()->new EntityNotFoundException("Department not found"));
        if (subjectDep.getDepartmentName().equals(Defaults.DEFAULT_DEPARTMENT)) new ResponseEntity<>(new DeletedDto("Default department cannot be deleted", depId.toString()), HttpStatus.NOT_ACCEPTABLE);

        Department defaultDepartment = departmentRepo.findByName(orgId, Defaults.DEFAULT_DEPARTMENT).orElseThrow(()->new EntityNotFoundException("Default Department not found, Please Create a Department with name 'DEFAULT'"));
        List<MemberProfile> memberProfiles = memberProfileRepo.findMemberByDepartmentName(orgId, subjectDep.getDepartmentName());

        for (MemberProfile m: memberProfiles) {
            m.setDepartment(defaultDepartment);
        }

        departmentRepo.delete(subjectDep);
        return new ResponseEntity<>(new DeletedDto("Department deleted successfully", depId.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DepartmentsDto> filterDepartment(String searchText, Long orgId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.DEPARTMENT_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new DepartmentsDto(departmentRepo.filter(orgId, searchText)), HttpStatus.OK);
    }

    @Override
    public Department createDefaultDepartment(Organization org) {
        Department newDep = new Department();
        newDep.setDepartmentName(Defaults.DEFAULT_DEPARTMENT);
        newDep.setOrganization(org);
        newDep.setCreatedBy(org.getCreatedBy());
        newDep.setUpdatedBy(org.getUpdatedBy());
        return departmentRepo.save(newDep);
    }
}
