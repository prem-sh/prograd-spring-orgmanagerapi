package io.github.premsh.orgmanager.dto.payroll;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JacksonXmlRootElement(localName = "Payroll")
public class CreatePayrollDto {

    @NotNull
    @DecimalMin(value = "1000.0")
    private Double basicPay;
    @DecimalMin(value = "0.0")
    private Double conveyanceAllowance;
    @DecimalMin(value = "0.0")
    private Double medicalAllowance;
    @DecimalMin(value = "0.0")
    private Double houseRentAllowance;
    @DecimalMin(value = "0.0")
    private Double foodAllowance;
    @DecimalMin(value = "0.0")
    private Double professionalTax;
    @DecimalMin(value = "0.0")
    private Double providentFund;
    @DecimalMin(value = "0.0")
    private Double employeeStateInsurance;
}
