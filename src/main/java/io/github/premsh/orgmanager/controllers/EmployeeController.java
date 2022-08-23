package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.employee.CreateEmployeeDto;
import io.github.premsh.orgmanager.dto.employee.EmployeeDto;
import io.github.premsh.orgmanager.dto.employee.EmployeesDto;
import io.github.premsh.orgmanager.dto.employee.UpdateEmployeeDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("org/{orgId}/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long orgId, @PathVariable Long id){
        return employeeService.getEmployeeById(orgId, id);
    }
    @GetMapping("/all")
    public ResponseEntity<EmployeesDto> getAllEmployees(@PathVariable Long orgId){
        return employeeService.getAllEmployees(orgId);
    }
    @GetMapping("/filter/{searchText}")
    public ResponseEntity<EmployeesDto> filterEmployees(
            @PathVariable Long orgId,
            @PathVariable String searchText,
            @RequestParam(name = "firstname", defaultValue = "true") Boolean firstname,
            @RequestParam(name = "lastname", defaultValue = "true") Boolean lastname,
            @RequestParam(name = "email", defaultValue = "true") Boolean email,
            @RequestParam(name = "address", defaultValue = "true") Boolean address,
            @RequestParam(name = "phone", defaultValue = "true") Boolean phone
    ){
        return employeeService.filterEmployees(orgId, searchText, firstname, lastname, email, address, phone);
    }
    @PostMapping
    public ResponseEntity<CreatedDto> createEmployee(@PathVariable Long orgId, @Valid @RequestBody CreateEmployeeDto dto){
        return employeeService.createEmployee(orgId, dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UpdatedDto> updateEmployee(@PathVariable Long orgId, @Valid @RequestBody UpdateEmployeeDto dto, @PathVariable Long id){
        return employeeService.updateEmployee(orgId, dto, id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DeletedDto> deleteEmployee(@PathVariable Long orgId,@PathVariable Long id) {
        return employeeService.deleteEmployee(orgId, id);
    }
    @GetMapping("/filter-by-position")
    public ResponseEntity<EmployeesDto> getEmployeesByDesignation(@PathVariable Long orgId, @RequestParam(name = "designation",required = false) String des, @RequestParam(name = "department",required = false) String dep){
        if(des !=null && dep==null) return employeeService.getEmployeesByDesignation(orgId, des);
        else if(des == null && dep!=null) return employeeService.getEmployeesByDepartment(orgId, dep);
        else return employeeService.getEmployeesByDesignationDepartment(orgId, des, dep);
    }


}
