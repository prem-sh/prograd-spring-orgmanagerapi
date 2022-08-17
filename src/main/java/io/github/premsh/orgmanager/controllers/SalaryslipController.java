package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.salaryslip.CreateSalaryslip;
import io.github.premsh.orgmanager.dto.salaryslip.SalaryslipDto;
import io.github.premsh.orgmanager.dto.salaryslip.SalaryslipsDto;
import io.github.premsh.orgmanager.dto.salaryslip.UpdateSalaryslip;
import io.github.premsh.orgmanager.services.SalaryslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/org/{orgId}/salaryslip")
public class SalaryslipController {
    @Autowired
    private SalaryslipService salaryslipService;

    @GetMapping("/{id}")
    public ResponseEntity<SalaryslipDto> getSalaryslipById(@PathVariable Long orgId, @PathVariable Long id) {
        return salaryslipService.getSalaryslipById(orgId, id);
    }

    @GetMapping("/all")
    public ResponseEntity<SalaryslipsDto> getAllSalaryslips(@PathVariable Long orgId) {
        return salaryslipService.getAllSalaryslips(orgId);
    }

    @GetMapping("/{empId}")
    public ResponseEntity<SalaryslipsDto> filterSalaryslips(@PathVariable Long orgId, @PathVariable Long empId) {
        return salaryslipService.findByEmployeeId(orgId, empId);
    }

    @PostMapping
    public ResponseEntity<CreatedDto> createSalaryslip(@PathVariable Long orgId, @Valid @RequestBody CreateSalaryslip dto) {
        return salaryslipService.createSalaryslip(orgId, dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatedDto> updateSalaryslip(@PathVariable Long orgId, @Valid @RequestBody UpdateSalaryslip dto, @PathVariable Long id) {
        return salaryslipService.updateSalaryslip(orgId, dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletedDto> deleteSalaryslip(@PathVariable Long orgId, @PathVariable Long id) {
        return salaryslipService.deleteSalaryslip(orgId, id);
    }
}
