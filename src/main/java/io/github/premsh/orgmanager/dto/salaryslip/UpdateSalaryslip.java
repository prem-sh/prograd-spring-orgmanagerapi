package io.github.premsh.orgmanager.dto.salaryslip;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@JacksonXmlRootElement(localName = "Salaryslip")
public class UpdateSalaryslip {

    private String slipUrl;

    private Boolean slipGenerated ;

    private Boolean slipDelivered ;
}
