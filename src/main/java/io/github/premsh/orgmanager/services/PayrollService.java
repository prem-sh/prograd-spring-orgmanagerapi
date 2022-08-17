package io.github.premsh.orgmanager.services;


import io.github.premsh.orgmanager.dto.payroll.CreatePayrollDto;
import io.github.premsh.orgmanager.dto.payroll.PayrollDto;
import io.github.premsh.orgmanager.dto.payroll.PayrollsDto;
import io.github.premsh.orgmanager.dto.payroll.UpdatePayrollDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import org.springframework.http.ResponseEntity;

public interface PayrollService {
    ResponseEntity<PayrollDto> getPayrollById(Long orgId, Long id);
    ResponseEntity<PayrollsDto> getAllPayrolls(Long orgId);
    ResponseEntity<CreatedDto> createPayroll(Long orgId, CreatePayrollDto dto);
    ResponseEntity<UpdatedDto> updatePayroll(Long orgId, UpdatePayrollDto dto, Long id);
    ResponseEntity<DeletedDto> deletePayroll(Long orgId, Long id);
}
