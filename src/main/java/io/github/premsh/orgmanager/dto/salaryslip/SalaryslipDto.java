package io.github.premsh.orgmanager.dto.salaryslip;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.metadata.Metadata;
import io.github.premsh.orgmanager.dto.payroll.PayrollDto;
import io.github.premsh.orgmanager.models.Employee;
import io.github.premsh.orgmanager.models.Payroll;
import io.github.premsh.orgmanager.models.Salaryslip;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.Date;

@Getter
@JacksonXmlRootElement(localName = "SalarySlip")
public class SalaryslipDto {
    @JacksonXmlProperty(isAttribute = true)
    private final Long id;
    @JacksonXmlProperty(isAttribute = true)
    private final Long employeeId;
    @JacksonXmlProperty(isAttribute = true)
    private final Boolean deleted;
    @JacksonXmlProperty(isAttribute = true)
    private Date date;

    private final String slipUrl;
    private final Boolean slipGenerated;
    private final Boolean slipDelivered;
    private final String name;
    private final String email;
    private final String phone;
    private final PayrollDto payroll;
    private final Metadata metadata;

    public SalaryslipDto(Salaryslip slip) {
        this.id = slip.getId();
        this.date = slip.getDate();
        this.slipUrl = slip.getSlipUrl();
        this.slipGenerated = slip.getSlipGenerated();
        this.slipDelivered = slip.getSlipDelivered();
        this.deleted = slip.getIsDeleted();
        this.date = slip.getDate();
        this.employeeId = slip.getEmployee().getId();
        this.name = slip.getEmployee().getFirstName()+" "+slip.getEmployee().getLastName();
        this.email = slip.getEmployee().getEmail();
        this.phone = slip.getEmployee().getPhone();
        this.payroll = new PayrollDto(slip.getPayroll());
        this.metadata = new Metadata(
                slip.getCreatedBy(),
                slip.getCreatedAt(),
                slip.getUpdatedBy(),
                slip.getUpdatedAt(),
                slip.getDeletedBy(),
                slip.getDeletedAt()
        );
    }
}
