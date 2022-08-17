package io.github.premsh.orgmanager.services;


import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.salaryslip.CreateSalaryslip;
import io.github.premsh.orgmanager.dto.salaryslip.SalaryslipDto;
import io.github.premsh.orgmanager.dto.salaryslip.SalaryslipsDto;
import io.github.premsh.orgmanager.dto.salaryslip.UpdateSalaryslip;
import org.springframework.http.ResponseEntity;

public interface SalaryslipService {
    ResponseEntity<SalaryslipDto> getSalaryslipById(Long orgId, Long id);
    ResponseEntity<SalaryslipsDto> getAllSalaryslips(Long orgId);
    public ResponseEntity<SalaryslipsDto> findByEmployeeId(Long orgId, Long empId);
    ResponseEntity<CreatedDto> createSalaryslip(Long orgId, CreateSalaryslip dto);
    ResponseEntity<UpdatedDto> updateSalaryslip(Long orgId, UpdateSalaryslip dto, Long id);
    ResponseEntity<DeletedDto> deleteSalaryslip(Long orgId, Long id);
}
