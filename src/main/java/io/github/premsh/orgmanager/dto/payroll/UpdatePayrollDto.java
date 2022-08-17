package io.github.premsh.orgmanager.dto.payroll;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JacksonXmlRootElement(localName = "Payroll")
public class UpdatePayrollDto {
    private Double basicPay;
    private Double conveyanceAllowance;
    private Double medicalAllowance;
    private Double houseRentAllowance;
    private Double foodAllowance;
    private Double professionalTax;
    private Double providentFund;
    private Double employeeStateInsurance;
}
