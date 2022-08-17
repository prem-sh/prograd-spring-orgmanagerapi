package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.payroll.CreatePayrollDto;
import io.github.premsh.orgmanager.dto.payroll.PayrollDto;
import io.github.premsh.orgmanager.dto.payroll.PayrollsDto;
import io.github.premsh.orgmanager.dto.payroll.UpdatePayrollDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.services.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/org/{orgId}/payroll")
public class PayrollController {
    @Autowired
    private PayrollService payrollService;

    @GetMapping("/{id}")
    public ResponseEntity<PayrollDto> getPayrollById(@PathVariable Long orgId, @PathVariable Long id) {
        return payrollService.getPayrollById(orgId, id);
    }

    @GetMapping("/all")
    public ResponseEntity<PayrollsDto> getAllPayrolls(@PathVariable Long orgId) {
        return payrollService.getAllPayrolls(orgId);
    }

    @PostMapping
    public ResponseEntity<CreatedDto> createPayroll(@PathVariable Long orgId, @Valid @RequestBody CreatePayrollDto dto) {
        return payrollService.createPayroll(orgId, dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatedDto> updatePayroll(@PathVariable Long orgId,  @Valid @RequestBody UpdatePayrollDto dto, @PathVariable Long id) {
        return payrollService.updatePayroll(orgId, dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletedDto> deletePayroll(@PathVariable Long orgId, @PathVariable Long id) {
        return payrollService.deletePayroll(orgId, id);
    }
}
