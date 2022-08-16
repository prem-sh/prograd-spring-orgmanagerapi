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
import io.github.premsh.orgmanager.repository.DepartmentRepo;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private OrganizationRepo organizationRepo;

    @Override
    public ResponseEntity<DepartmentsDto> getAllDepartment(Long orgId) {
        return new ResponseEntity<>(new DepartmentsDto(departmentRepo.findAll(orgId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DepartmentDto> getDepartment(Long orgId, Long depId) {
        return new ResponseEntity<>(new DepartmentDto(departmentRepo.findById(orgId, depId).orElseThrow(()->new EntityNotFoundException("Department not found"))), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedDto> createDepartment(Long orgId, CreateDepartmentDto depDto) {
        Department newDep = new Department();
        newDep.setDepartmentName(depDto.getName());
        newDep.setOrganization(
                organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found"))
        );
        departmentRepo.save(newDep);
        return new ResponseEntity<>(new CreatedDto("Department created successfully", String.valueOf(newDep.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateDepartment(Long orgId, UpdateDepartmentDto depDto, Long depId) {
        Department subjectDep = departmentRepo.findById(orgId, depId).orElseThrow(()->new EntityNotFoundException("Department not found"));
        subjectDep.setDepartmentName(depDto.getName());
        departmentRepo.save(subjectDep);
        return new ResponseEntity<>(new UpdatedDto("Department name updated successfully", depId.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DeletedDto> deleteDepartment(Long orgId, Long depId) {
        if(departmentRepo.existsById(orgId, depId)) departmentRepo.deleteById(depId);
        else throw new EntityNotFoundException("Department not found");
        return new ResponseEntity<>(new DeletedDto("Department deleted successfully", depId.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DepartmentsDto> filterDepartment(String searchText, Long orgId) {
        return new ResponseEntity<>(new DepartmentsDto(departmentRepo.filter(orgId, searchText)), HttpStatus.OK);
    }
}
