package io.github.premsh.orgmanager.dto.memberprofile;

import io.github.premsh.orgmanager.models.*;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
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

    private String ifsc;
}
