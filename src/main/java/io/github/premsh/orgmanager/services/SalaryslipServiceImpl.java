package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.salaryslip.CreateSalaryslip;
import io.github.premsh.orgmanager.dto.salaryslip.SalaryslipDto;
import io.github.premsh.orgmanager.dto.salaryslip.SalaryslipsDto;
import io.github.premsh.orgmanager.dto.salaryslip.UpdateSalaryslip;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Salaryslip;
import io.github.premsh.orgmanager.repository.EmployeeRepo;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import io.github.premsh.orgmanager.repository.PayrollRepo;
import io.github.premsh.orgmanager.repository.SalaryslipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SalaryslipServiceImpl implements SalaryslipService{
    @Autowired
    private SalaryslipRepo salaryslipRepo;
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private PayrollRepo payrollRepo;

    @Override
    public ResponseEntity<SalaryslipDto> getSalaryslipById(Long orgId, Long id) {
        return new ResponseEntity<>(
                new SalaryslipDto(salaryslipRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Slip not found"))), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<SalaryslipsDto> getAllSalaryslips(Long orgId) {
        return new ResponseEntity<>(
                new SalaryslipsDto(salaryslipRepo.findAll(orgId)), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<SalaryslipsDto> findByEmployeeId(Long orgId, Long empId) {
        return new ResponseEntity<>(
                new SalaryslipsDto(salaryslipRepo.findAllByEmployeeId(orgId, empId)), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CreatedDto> createSalaryslip(Long orgId, CreateSalaryslip dto) {
        Salaryslip salaryslip = new Salaryslip();
        salaryslip.setCreatedBy(principalService.getUser());
        salaryslip.setUpdatedBy(principalService.getUser());
        salaryslip.setEmployee(employeeRepo.findById(orgId, dto.getEmpId()).orElseThrow(()->new EntityNotFoundException("Employee not found")));
        salaryslip.setPayroll(payrollRepo.findById(orgId, dto.getPayrollId()).orElseThrow(()->new EntityNotFoundException("Payroll not found")));
        salaryslip.setDate(dto.getDate());
        if (dto.getSlipUrl()!=null) salaryslip.setSlipUrl(dto.getSlipUrl());
        if (dto.getSlipGenerated()!=null) salaryslip.setSlipGenerated(dto.getSlipGenerated());
        if (dto.getSlipDelivered()!=null) salaryslip.setSlipDelivered(dto.getSlipDelivered());
        Salaryslip salaryslip1 = salaryslipRepo.save(salaryslip);
        return new ResponseEntity<>(new CreatedDto("Salaryslip created successfully",String.valueOf(salaryslip1.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateSalaryslip(Long orgId, UpdateSalaryslip dto, Long id) {
        if (!salaryslipRepo.existsById(orgId, id)) throw new EntityNotFoundException("Salaryslip not found");
        Salaryslip salaryslip = salaryslipRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Salaryslip not found"));
        salaryslip.setUpdatedBy(principalService.getUser());
        if (dto.getSlipUrl()!=null) salaryslip.setSlipUrl(dto.getSlipUrl());
        if (dto.getSlipGenerated()!=null) salaryslip.setSlipGenerated(dto.getSlipGenerated());
        if (dto.getSlipDelivered()!=null) salaryslip.setSlipDelivered(dto.getSlipDelivered());
        return new ResponseEntity<>(new UpdatedDto("Salaryslip update successfully",String.valueOf(salaryslip.getId())), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DeletedDto> deleteSalaryslip(Long orgId, Long id) {
        if (!salaryslipRepo.existsById(orgId, id)) throw new EntityNotFoundException("Salaryslip not found");
        Salaryslip salaryslip = salaryslipRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Salaryslip not found"));
        salaryslip.setDeletedAt(new Date());
        salaryslip.setDeletedBy(principalService.getUser());
        salaryslip.setIsDeleted(true);
        return new ResponseEntity<>(new DeletedDto("Salaryslip deleted successfully",String.valueOf(salaryslip.getId())), HttpStatus.ACCEPTED);
    }
}
