package io.github.premsh.orgmanager.dto.memberprofile;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Payroll;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Membership")
public class UpdateMemberProfileDto {
    private String role;

    private String designation;

    private String department;

    private Long payrollId;

    private String panNumber;

    private String bankAccountNumber;

    private String ifsc;
}
