package io.github.premsh.orgmanager.dto.payroll;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.metadata.Metadata;
import io.github.premsh.orgmanager.dto.organization.OrganizationCompactDto;
import io.github.premsh.orgmanager.dto.user.UserCompactDto;
import io.github.premsh.orgmanager.models.Department;
import io.github.premsh.orgmanager.models.Organization;
import io.github.premsh.orgmanager.models.Payroll;
import io.github.premsh.orgmanager.models.User;
import lombok.Getter;
import org.springframework.http.MediaType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@JacksonXmlRootElement(localName = "Payroll")
@Getter
public class PayrollDto {
    @JacksonXmlProperty(isAttribute = true)
    private final long id;
    private final double basicPay;
    private final double conveyanceAllowance;
    private final double medicalAllowance;
    private final double houseRentAllowance;
    private final double foodAllowance;
    private final double professionalTax;
    private final double providentFund;
    private final double employeeStateInsurance;
    private final OrganizationCompactDto organization;
    private final Metadata metadata;

    public PayrollDto(Payroll payroll) {
        this.id = payroll.getId();
        this.basicPay = payroll.getBasicPay();
        this.conveyanceAllowance = payroll.getConveyanceAllowance();
        this.medicalAllowance = payroll.getMedicalAllowance();
        this.houseRentAllowance = payroll.getHouseRentAllowance();
        this.foodAllowance = payroll.getFoodAllowance();
        this.professionalTax = payroll.getProfessionalTax();
        this.providentFund = payroll.getProvidentFund();
        this.employeeStateInsurance = payroll.getEmployeeStateInsurance();
        this.organization = new OrganizationCompactDto(payroll.getOrganization());
        this.metadata = new Metadata(
                payroll.getCreatedBy(),
                payroll.getCreatedAt(),
                payroll.getUpdatedBy(),
                payroll.getUpdatedAt(),
                payroll.getDeletedBy(),
                payroll.getDeletedAt()
        );
    }
}
