package io.github.premsh.orgmanager.dto.employee;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.constants.ValidationMessage;
import io.github.premsh.orgmanager.dto.department.DepartmentDto;
import io.github.premsh.orgmanager.dto.designation.DesignationDto;
import io.github.premsh.orgmanager.dto.organization.OrganizationCompactDto;
import io.github.premsh.orgmanager.dto.payroll.PayrollDto;
import io.github.premsh.orgmanager.models.*;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@JacksonXmlRootElement(localName = "Employee")
public class EmployeeDto {

    @JacksonXmlProperty(isAttribute = true)
    private final Long id;
    @JacksonXmlProperty(isAttribute = true)
    private final Boolean deleted;
    @JacksonXmlProperty(isAttribute = true)
    private final String email;

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String phone;

    private final String panNumber;
    private final String bankAccountNumber;
    private final String ifsc;

    private final OrganizationCompactDto organization;
    private final DesignationDto designation;
    private final DepartmentDto department;
    private final PayrollDto payroll;

    public EmployeeDto(Employee e) {
        this.id = e.getId();
        this.deleted = e.getIsDeleted();
        this.email = e.getEmail();
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.address = e.getAddress();
        this.phone = e.getPhone();
        this.panNumber = e.getPanNumber();
        this.bankAccountNumber = e.getBankAccountNumber();
        this.ifsc = e.getIfsc();
        this.organization = new OrganizationCompactDto(e.getOrganization());
        this.designation = new DesignationDto(e.getDesignation());
        this.department = new DepartmentDto(e.getDepartment());
        this.payroll = new PayrollDto(e.getPayroll());
    }
}
