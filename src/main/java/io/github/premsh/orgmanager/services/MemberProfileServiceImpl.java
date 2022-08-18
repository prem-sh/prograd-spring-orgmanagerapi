package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.employee.EmployeeDto;
import io.github.premsh.orgmanager.dto.employee.EmployeesDto;
import io.github.premsh.orgmanager.dto.memberprofile.CreateMemberProfileDto;
import io.github.premsh.orgmanager.dto.memberprofile.MemberProfileDto;
import io.github.premsh.orgmanager.dto.memberprofile.MemberProfilesDto;
import io.github.premsh.orgmanager.dto.memberprofile.UpdateMemberProfileDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Employee;
import io.github.premsh.orgmanager.models.MemberProfile;
import io.github.premsh.orgmanager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MemberProfileServiceImpl implements MemberProfileService{

    @Autowired
    private MemberProfileRepo memberProfileRepo;
    @Autowired
    private UserRepo userRepo;
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
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public ResponseEntity<MemberProfilesDto> getAllMembers(Long orgId) {
        System.out.println("Entry");
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(
                new MemberProfilesDto(memberProfileRepo.findAll(orgId)), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<MemberProfileDto> getMemberById(Long orgId, Long memId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(
                new MemberProfileDto(memberProfileRepo.findById(orgId, memId).orElseThrow(()->new EntityNotFoundException("Member not found"))), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CreatedDto> createMembership(Long orgId, CreateMemberProfileDto dto) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        MemberProfile member = new MemberProfile();
        member.setCreatedBy(principalService.getUser());
        member.setUpdatedBy(principalService.getUser());
        member.setOrganization(organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found")));
        member.setUser(userRepo.findByEmail(dto.getEmail()).orElseThrow(()->new EntityNotFoundException("User not found")));
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
        return new ResponseEntity<>(new CreatedDto("Nwe Member added successfully",String.valueOf(newMem.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateMembership(Long orgId, UpdateMemberProfileDto dto, Long memId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        MemberProfile member = memberProfileRepo.findById(orgId, memId).orElseThrow(()->new EntityNotFoundException("Membership id does not exist"));
        member.setUpdatedBy(principalService.getUser());
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
        return new ResponseEntity<>(new UpdatedDto("Member profile Updated successfully",String.valueOf(member.getId())), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DeletedDto> removeMember(Long orgId, Long memId) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        MemberProfile member = memberProfileRepo.findById(orgId, memId).orElseThrow(()->new EntityNotFoundException("Membership id does not exist"));
        member.setDeletedBy(principalService.getUser());
        member.setDeletedAt(new Date());
        member.setIsDeleted(true);
        memberProfileRepo.save(member);
        return new ResponseEntity<>(new DeletedDto("Member profile Deleted successfully",String.valueOf(member.getId())), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<MemberProfilesDto> filterMembers(Long orgId, String filterText, Boolean byName, Boolean byEmail, Boolean byPhone, Boolean byAddress) {
        if (! principalService.isMemberOfOrg(orgId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        return null;
    }
}
