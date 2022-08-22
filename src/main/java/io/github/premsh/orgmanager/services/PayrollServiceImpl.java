package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.constants.Permissions;
import io.github.premsh.orgmanager.dto.AuthDto;
import io.github.premsh.orgmanager.dto.payroll.CreatePayrollDto;
import io.github.premsh.orgmanager.dto.payroll.PayrollDto;
import io.github.premsh.orgmanager.dto.payroll.PayrollsDto;
import io.github.premsh.orgmanager.dto.payroll.UpdatePayrollDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Payroll;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import io.github.premsh.orgmanager.repository.PayrollRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PayrollServiceImpl implements PayrollService{
    @Autowired
    PayrollRepo payrollRepo;
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;

    @Override
    public ResponseEntity<PayrollDto> getPayrollById(Long orgId, Long id) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.PAYROLL_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(
                new PayrollDto(payrollRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Payroll not found"))), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<PayrollsDto> getAllPayrolls(Long orgId) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.PAYROLL_READ);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(
                new PayrollsDto(payrollRepo.findAll(orgId)), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CreatedDto> createPayroll(Long orgId, CreatePayrollDto dto) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.PAYROLL_CREATE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Payroll p = new Payroll();
        p.setOrganization(organizationRepo.findById(orgId).orElseThrow(()->new EntityNotFoundException("Organization not found")));
        p.setCreatedBy(auth.getUserId());
        p.setUpdatedBy(auth.getUserId());
        p.setBasicPay(dto.getBasicPay());
        if(dto.getConveyanceAllowance() != null) p.setConveyanceAllowance(dto.getConveyanceAllowance());
        if(dto.getMedicalAllowance() != null) p.setMedicalAllowance(dto.getMedicalAllowance());
        if(dto.getHouseRentAllowance() != null) p.setHouseRentAllowance(dto.getHouseRentAllowance());
        if(dto.getFoodAllowance() != null) p.setFoodAllowance(dto.getFoodAllowance());
        if(dto.getProfessionalTax() != null) p.setProfessionalTax(dto.getProfessionalTax());
        if(dto.getEmployeeStateInsurance() != null) p.setEmployeeStateInsurance(dto.getEmployeeStateInsurance());
        if(dto.getProvidentFund() != null) p.setProvidentFund(dto.getProvidentFund());
        Payroll newPayroll = payrollRepo.save(p);
        return new ResponseEntity<>(new CreatedDto("Payroll created successfully",String.valueOf(newPayroll.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updatePayroll(Long orgId, UpdatePayrollDto dto, Long id) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.PAYROLL_UPDATE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Payroll p = payrollRepo.findById(orgId, id).orElseThrow(()-> new EntityNotFoundException("Payroll not found"));
        p.setUpdatedBy(auth.getUserId());
        if(dto.getBasicPay() != null) p.setBasicPay(dto.getBasicPay());
        if(dto.getConveyanceAllowance() != null) p.setConveyanceAllowance(dto.getConveyanceAllowance());
        if(dto.getMedicalAllowance() != null) p.setMedicalAllowance(dto.getMedicalAllowance());
        if(dto.getHouseRentAllowance() != null) p.setHouseRentAllowance(dto.getHouseRentAllowance());
        if(dto.getFoodAllowance() != null) p.setFoodAllowance(dto.getFoodAllowance());
        if(dto.getProfessionalTax() != null) p.setProfessionalTax(dto.getProfessionalTax());
        if(dto.getEmployeeStateInsurance() != null) p.setEmployeeStateInsurance(dto.getEmployeeStateInsurance());
        if(dto.getProvidentFund() != null) p.setProvidentFund(dto.getProvidentFund());
        payrollRepo.save(p);
        return new ResponseEntity<>(new UpdatedDto("Payroll updated successfully",String.valueOf(p.getId())), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DeletedDto> deletePayroll(Long orgId, Long id) {
        AuthDto auth = principalService.checkAuthority(orgId, Permissions.PAYROLL_DELETE);
        if (!auth.isAuthority()) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        Payroll p = payrollRepo.findById(orgId, id).orElseThrow(()-> new EntityNotFoundException("Payroll not found"));
        payrollRepo.delete(p);
        return new ResponseEntity<>(new DeletedDto("Payroll deleted successfully",String.valueOf(p.getId())), HttpStatus.ACCEPTED);
    }
}
