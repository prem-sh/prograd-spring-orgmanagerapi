package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.constants.RoleConstants;
import io.github.premsh.orgmanager.dto.employee.CreateEmployeeDto;
import io.github.premsh.orgmanager.dto.employee.EmployeeDto;
import io.github.premsh.orgmanager.dto.employee.EmployeesDto;
import io.github.premsh.orgmanager.dto.employee.UpdateEmployeeDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Designation;
import io.github.premsh.orgmanager.models.MemberProfile;
import io.github.premsh.orgmanager.models.User;
import io.github.premsh.orgmanager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private MemberProfileRepo memberProfileRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private DesignationRepo designationRepo;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private PayrollRepo payrollRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(Long orgId, Long id) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        MemberProfile m = memberProfileRepo.findByOrgIdUserIdRoleId(orgId, id, RoleConstants.EMPLOYEE).orElseThrow(
                ()-> new EntityNotFoundException("Employee not found")
        );
        return new ResponseEntity<>(
                new EmployeeDto(m), HttpStatus.OK
        ) ;
    }

    @Override
    public ResponseEntity<EmployeesDto> getAllEmployees(Long orgId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        List<MemberProfile> m = memberProfileRepo.findByOrgIdRoleName(orgId, RoleConstants.EMPLOYEE);
        return new ResponseEntity<>(
                new EmployeesDto(m), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<EmployeesDto> filterEmployees(Long orgId, String searchText, Boolean firstname, Boolean lastname, Boolean email, Boolean address, Boolean phone) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        List<MemberProfile> filtrate = new ArrayList<>();
        if(firstname) filtrate.addAll(memberProfileRepo.filterFirstname(orgId,searchText));
        if(lastname) filtrate.addAll(memberProfileRepo.filterLastname(orgId,searchText));
        if(email) filtrate.addAll(memberProfileRepo.filterEmail(orgId,searchText));
        if(address) filtrate.addAll(memberProfileRepo.filterAddress(orgId,searchText));
        if(phone) filtrate.addAll(memberProfileRepo.filterPhone(orgId,searchText));

        return new ResponseEntity<>(
                new EmployeesDto(filtrate), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CreatedDto> createEmployee(Long orgId, CreateEmployeeDto dto) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Long actionBy = principalService.getUser().getId();

        User newUser = dto.getUser();
        Optional<User> optionalUser = userRepo.findByEmail(newUser.getEmail());
        if(optionalUser.isPresent()){
            newUser = optionalUser.get();
        }else{
            if(userRepo.existsByEmail(newUser.getEmail())){
                return new ResponseEntity<>(new CreatedDto("User email already taken"), HttpStatus.NOT_ACCEPTABLE);
            }
            if(userRepo.existsByEmail(newUser.getPhone())){
                return new ResponseEntity<>(new CreatedDto("User phone number already taken"), HttpStatus.NOT_ACCEPTABLE);
            }
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            newUser.setCreatedBy(actionBy);
            newUser.setUpdatedBy(actionBy);
            newUser = userRepo.save(newUser);
        }


        MemberProfile member = new MemberProfile();
        member.setCreatedBy(actionBy);
        member.setUpdatedBy(actionBy);
        member.setOrganization(organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found")));
        member.setUser(newUser);
        member.setRole(roleRepo.findByRoleName(dto.getRole()).orElseThrow(()->new EntityNotFoundException("Role does not exist")));
        if (dto.getDesignation()!=null) member.setDesignation(designationRepo.findByName(orgId, dto.getDesignation()).orElseThrow(
                ()->new EntityNotFoundException("Designation not found")
        ));
        if (dto.getDepartment()!=null) member.setDepartment(departmentRepo.findByName(orgId, dto.getDepartment()).orElseThrow(
                ()->new EntityNotFoundException("Department not found")
        ));
        if (dto.getPayrollId()!=null) member.setPayroll(payrollRepo.findById(orgId, dto.getPayrollId()).orElseThrow(
                ()->new EntityNotFoundException("Payroll profile not found")
        ));
        if (dto.getPanNumber()!=null) member.setPanNumber(dto.getPanNumber());
        if (dto.getBankAccountNumber()!=null) member.setBankAccountNumber(dto.getBankAccountNumber());
        if (dto.getIfsc()!=null) member.setIfsc(dto.getIfsc());
        MemberProfile newMem =  memberProfileRepo.save(member);

        return new ResponseEntity<>(new CreatedDto("Employee created successfully",String.valueOf(newMem.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateEmployee(Long orgId, UpdateEmployeeDto dto, Long id) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        MemberProfile member = memberProfileRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Membership id does not exist"));

        Long actionBy = principalService.getUser().getId();

        member.setUpdatedBy(actionBy);
        if (dto.getRole()!=null)member.setRole(roleRepo.findByRoleName(dto.getRole()).orElseThrow(()->new EntityNotFoundException("Role does not exist")));
        if (dto.getDesignation()!=null) member.setDesignation(designationRepo.findByName(orgId, dto.getDesignation()).orElseThrow(
                ()->new EntityNotFoundException("Designation not found")
        ));
        if (dto.getDepartment()!=null) member.setDepartment(departmentRepo.findByName(orgId, dto.getDepartment()).orElseThrow(
                ()->new EntityNotFoundException("Department not found")
        ));
        if (dto.getPayrollId()!=null) member.setPayroll(payrollRepo.findById(orgId, dto.getPayrollId()).orElseThrow(
                ()->new EntityNotFoundException("Payroll profile not found")
        ));
        if (dto.getPanNumber()!=null) member.setPanNumber(dto.getPanNumber());
        if (dto.getBankAccountNumber()!=null) member.setBankAccountNumber(dto.getBankAccountNumber());
        if (dto.getIfsc()!=null) member.setIfsc(dto.getIfsc());
        memberProfileRepo.save(member);

        User subjectUser = userRepo.findById(id).orElseThrow(()->new EntityNotFoundException(String.format("User with id %d does not exist", id)));
        dto.getUser(subjectUser); //get updates from dto
        subjectUser.setPassword(passwordEncoder.encode(subjectUser.getPassword()));
        subjectUser.setUpdatedBy(actionBy);
        userRepo.save(subjectUser);

        return new ResponseEntity<>(new UpdatedDto("Employee updated successfully",String.valueOf(member.getId())), HttpStatus.ACCEPTED);
    }

//    @Override
//    public ResponseEntity<DeletedDto> deleteEmployee(Long orgId, Long id) {
//
//        MemberProfile member = memberProfileRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Membership id does not exist"));
//        member.setDeletedBy(principalService.getUser());
//        member.setDeletedAt(new Date());
//        member.setIsDeleted(true);
//        memberProfileRepo.save(member);
//
//        return new ResponseEntity<>(new DeletedDto("Employee deleted successfully",String.valueOf(member.getId())), HttpStatus.ACCEPTED);
//    }

    @Override
    public ResponseEntity<DeletedDto> deleteEmployee(Long orgId, Long id) {

        Long actionBy = principalService.getUser().getId();

        MemberProfile member = memberProfileRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Membership id does not exist"));
        member.setDeletedBy(actionBy);
        member.setDeletedAt(new Date());
        member.setIsDeleted(true);
        memberProfileRepo.save(member);

        return new ResponseEntity<>(new DeletedDto("Employee deleted successfully",String.valueOf(member.getId())), HttpStatus.ACCEPTED);
    }
}
