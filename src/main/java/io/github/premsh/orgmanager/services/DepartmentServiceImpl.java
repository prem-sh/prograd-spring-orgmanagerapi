package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.department.CreateDepartmentDto;
import io.github.premsh.orgmanager.dto.department.DepartmentDto;
import io.github.premsh.orgmanager.dto.department.DepartmentsDto;
import io.github.premsh.orgmanager.dto.department.UpdateDepartmentDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Department;
import io.github.premsh.orgmanager.models.User;
import io.github.premsh.orgmanager.repository.DepartmentRepo;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import io.github.premsh.orgmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;

    @Override
    public ResponseEntity<DepartmentsDto> getAllDepartment(Long orgId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new DepartmentsDto(departmentRepo.findAll(orgId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DepartmentDto> getDepartment(Long orgId, Long depId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new DepartmentDto(departmentRepo.findById(orgId, depId).orElseThrow(()->new EntityNotFoundException("Department not found"))), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedDto> createDepartment(Long orgId, CreateDepartmentDto depDto) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Department newDep = new Department();
        newDep.setDepartmentName(depDto.getName());
        newDep.setOrganization(
                organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found"))
        );

        newDep.setCreatedBy(principalService.getUser());
        newDep.setUpdatedBy(principalService.getUser());

        departmentRepo.save(newDep);
        return new ResponseEntity<>(new CreatedDto("Department created successfully", String.valueOf(newDep.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateDepartment(Long orgId, UpdateDepartmentDto depDto, Long depId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Department subjectDep = departmentRepo.findById(orgId, depId).orElseThrow(()->new EntityNotFoundException("Department not found"));
        subjectDep.setDepartmentName(depDto.getName());
        subjectDep.setUpdatedBy(principalService.getUser());
        departmentRepo.save(subjectDep);
        return new ResponseEntity<>(new UpdatedDto("Department updated successfully", depId.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DeletedDto> deleteDepartment(Long orgId, Long depId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Department subjectDep = departmentRepo.findById(orgId, depId).orElseThrow(()->new EntityNotFoundException("Department not found"));
        subjectDep.setIsDeleted(true);
        subjectDep.setDeletedBy(principalService.getUser());
        subjectDep.setDeletedAt(new Date());
        departmentRepo.save(subjectDep);
        return new ResponseEntity<>(new DeletedDto("Department deleted successfully", depId.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DepartmentsDto> filterDepartment(String searchText, Long orgId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(new DepartmentsDto(departmentRepo.filter(orgId, searchText)), HttpStatus.OK);
    }
}
