package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.department.CreateDepartmentDto;
import io.github.premsh.orgmanager.dto.department.DepartmentDto;
import io.github.premsh.orgmanager.dto.department.DepartmentsDto;
import io.github.premsh.orgmanager.dto.department.UpdateDepartmentDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("org/{orgId}/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/all")
    public ResponseEntity<DepartmentsDto> getAllDepartment(@PathVariable Long orgId) {
        return departmentService.getAllDepartment(orgId);
    }
    @GetMapping("/{depId}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable Long orgId, @PathVariable Long depId) {
        return departmentService.getDepartment(orgId, depId);
    }
    @PostMapping
    public ResponseEntity<CreatedDto> createDepartment(@PathVariable Long orgId, @Valid @RequestBody CreateDepartmentDto depDto) {
        return departmentService.createDepartment(orgId, depDto);
    }
    @PutMapping("/{depId}")
    public ResponseEntity<UpdatedDto> updateDepartment(@PathVariable Long orgId, @Valid @RequestBody UpdateDepartmentDto depDto, @PathVariable Long depId) {
        return departmentService.updateDepartment(orgId, depDto, depId);
    }
    @DeleteMapping("/{depId}")
    public ResponseEntity<DeletedDto> deleteDepartment(@PathVariable Long orgId, @PathVariable Long depId) {
        return departmentService.deleteDepartment(orgId, depId);
    }
    @GetMapping("/filter/{searchText}")
    public ResponseEntity<DepartmentsDto> filterDepartment(@PathVariable Long orgId, @PathVariable String searchText) {
        return departmentService.filterDepartment(searchText, orgId);
    }
}