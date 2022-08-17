package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.employee.CreateEmployeeDto;
import io.github.premsh.orgmanager.dto.employee.EmployeeDto;
import io.github.premsh.orgmanager.dto.employee.EmployeesDto;
import io.github.premsh.orgmanager.dto.employee.UpdateEmployeeDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {
    ResponseEntity<EmployeeDto> getEmployeeById(Long orgId, Long id);
    ResponseEntity<EmployeesDto> getAllEmployees(Long orgId);
    ResponseEntity<EmployeesDto> filterEmployees(Long orgId, String searchText,Boolean firstname,Boolean lastname,Boolean email,Boolean address,Boolean phone);
    ResponseEntity<CreatedDto> createEmployee(Long orgId, CreateEmployeeDto dto);
    ResponseEntity<UpdatedDto> updateEmployee(Long orgId, UpdateEmployeeDto dto, Long id);
    ResponseEntity<DeletedDto> deleteEmployee(Long orgId, Long id);
}
