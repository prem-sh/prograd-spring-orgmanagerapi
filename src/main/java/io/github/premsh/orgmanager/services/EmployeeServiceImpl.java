package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.employee.CreateEmployeeDto;
import io.github.premsh.orgmanager.dto.employee.EmployeeDto;
import io.github.premsh.orgmanager.dto.employee.EmployeesDto;
import io.github.premsh.orgmanager.dto.employee.UpdateEmployeeDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Designation;
import io.github.premsh.orgmanager.models.Employee;
import io.github.premsh.orgmanager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private DesignationRepo designationRepo;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private PayrollRepo payrollRepo;

    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(Long orgId, Long id) {
        return new ResponseEntity<>(
                new EmployeeDto(employeeRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Employee not found"))), HttpStatus.OK
        ) ;
    }

    @Override
    public ResponseEntity<EmployeesDto> getAllEmployees(Long orgId) {
        return new ResponseEntity<>(
                new EmployeesDto(employeeRepo.findAll(orgId)), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<EmployeesDto> filterEmployees(Long orgId, String searchText, Boolean firstname, Boolean lastname, Boolean email, Boolean address, Boolean phone) {
        List<Employee> filtrate = new ArrayList<>();
        if(firstname) filtrate.addAll(employeeRepo.filterFirstname(orgId,searchText));
        if(lastname) filtrate.addAll(employeeRepo.filterLastname(orgId,searchText));
        if(email) filtrate.addAll(employeeRepo.filterEmail(orgId,searchText));
        if(address) filtrate.addAll(employeeRepo.filterAddress(orgId,searchText));
        if(phone) filtrate.addAll(employeeRepo.filterPhone(orgId,searchText));

        return new ResponseEntity<>(
                new EmployeesDto(filtrate), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CreatedDto> createEmployee(Long orgId, CreateEmployeeDto dto) {
        Employee employee = new Employee();
        employee.setCreatedBy(principalService.getUser());
        employee.setUpdatedBy(principalService.getUser());
        employee.setOrganization(organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found")));
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setAddress(dto.getAddress());
        employee.setPhone(dto.getPhone());
        employee.setEmail(dto.getEmail());
        employee.setDesignation(designationRepo.findById(orgId, dto.getDesignationId()).orElseThrow(
                ()->new EntityNotFoundException("Designation not found")
        ));
        employee.setDepartment(departmentRepo.findById(orgId, dto.getDepartmentId()).orElseThrow(
                ()->new EntityNotFoundException("Department not found")
        ));
        employee.setPayroll(payrollRepo.findById(orgId, dto.getPayrollId()).orElseThrow(
                ()->new EntityNotFoundException("Payroll profile not found")
        ));
        employee.setPanNumber(dto.getPanNumber());
        employee.setBankAccountNumber(dto.getBankAccountNumber());
        employee.setIfsc(dto.getIfsc());
        Employee newEmp =  employeeRepo.save(employee);
        return new ResponseEntity<>(new CreatedDto("Employee created successfully",String.valueOf(newEmp.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateEmployee(Long orgId, UpdateEmployeeDto dto, Long id) {
        Employee e = employeeRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Employee not found"));
        e.setUpdatedBy(principalService.getUser());
        if(dto.getFirstName()!=null) e.setFirstName(dto.getFirstName());
        if(dto.getLastName()!=null) e.setLastName(dto.getLastName());
        if(dto.getAddress()!=null) e.setAddress(dto.getAddress());
        if(dto.getPhone()!=null) e.setPhone(dto.getPhone());
        if(dto.getEmail()!=null) e.setEmail(dto.getEmail());
        if(dto.getDesignationId()!=null) e.setDesignation(designationRepo.findById(orgId, dto.getDesignationId()).orElseThrow(
                ()->new EntityNotFoundException("Designation not found")
        ));
        if(dto.getDepartmentId()!=null) e.setDepartment(departmentRepo.findById(orgId, dto.getDepartmentId()).orElseThrow(
                ()->new EntityNotFoundException("Department not found")
        ));
        if(dto.getPayrollId()!=null) e.setPayroll(payrollRepo.findById(orgId, dto.getPayrollId()).orElseThrow(
                ()->new EntityNotFoundException("Payroll profile not found")
        ));
        if(dto.getPanNumber()!=null) e.setPanNumber(dto.getPanNumber());
        if(dto.getBankAccountNumber()!=null) e.setBankAccountNumber(dto.getBankAccountNumber());
        if(dto.getIfsc()!=null) e.setIfsc(dto.getIfsc());
        employeeRepo.save(e);
        return new ResponseEntity<>(new UpdatedDto("Employee updated successfully",String.valueOf(e.getId())), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DeletedDto> deleteEmployee(Long orgId, Long id) {
        if (!employeeRepo.existsById(orgId, id)) throw new EntityNotFoundException("Employee not found");
        Employee e = employeeRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Employee not found"));
        e.setDeletedBy(principalService.getUser());
        e.setDeletedAt(new Date());
        e.setIsDeleted(true);
        employeeRepo.save(e);
        return new ResponseEntity<>(new DeletedDto("Employee deleted successfully",String.valueOf(e.getId())), HttpStatus.ACCEPTED);
    }
}
