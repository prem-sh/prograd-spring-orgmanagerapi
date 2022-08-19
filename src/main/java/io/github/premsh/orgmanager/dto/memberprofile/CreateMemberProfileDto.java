package io.github.premsh.orgmanager.dto.memberprofile;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.*;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JacksonXmlRootElement(localName = "Membership")
public class CreateMemberProfileDto {
    @NotNull
    @Email
    private String email;

    @NotNull
    private String role;

    private String designation;

    private String department;

    private Long payrollId;

    private String panNumber;

    private String bankAccountNumber;
    @Size(max = 11)
    private String ifsc;
}
