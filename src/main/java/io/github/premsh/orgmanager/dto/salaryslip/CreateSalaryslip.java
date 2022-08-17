package io.github.premsh.orgmanager.dto.salaryslip;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Employee;
import io.github.premsh.orgmanager.models.Payroll;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@JacksonXmlRootElement(localName = "Salaryslip")
public class CreateSalaryslip {
    @NotNull
    private Long empId;
    @NotNull
    private Long payrollId;
    @NotNull
    private Date date;

    private String slipUrl;

    private Boolean slipGenerated ;

    private Boolean slipDelivered ;
}
