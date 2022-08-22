package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.department.CreateDepartmentDto;
import io.github.premsh.orgmanager.dto.department.DepartmentDto;
import io.github.premsh.orgmanager.dto.department.DepartmentsDto;
import io.github.premsh.orgmanager.dto.department.UpdateDepartmentDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.models.Department;
import io.github.premsh.orgmanager.models.Organization;
import org.springframework.http.ResponseEntity;

public interface DepartmentService {
    ResponseEntity<DepartmentsDto> getAllDepartment(Long orgId);
    ResponseEntity<DepartmentDto> getDepartment(Long orgId, Long depId);
    ResponseEntity<CreatedDto> createDepartment(Long orgId, CreateDepartmentDto depDto);
    ResponseEntity<UpdatedDto> updateDepartment(Long orgId, UpdateDepartmentDto depDto, Long depId);
    ResponseEntity<DeletedDto> deleteDepartment(Long orgId, Long depId);
    ResponseEntity<DepartmentsDto> filterDepartment(String searchText, Long orgId);

    Department createDefaultDepartment(Organization org);
}
