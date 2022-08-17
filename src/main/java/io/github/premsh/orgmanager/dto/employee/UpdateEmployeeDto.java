package io.github.premsh.orgmanager.dto.employee;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.constants.ValidationMessage;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JacksonXmlRootElement(localName = "Employee")
public class UpdateEmployeeDto {

    @NotNull
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Size(max = 50)
    private String lastName;

    private String address;

    @NotNull
    @Size(max = 15, message = ValidationMessage.PHONE_LENGTH_CONSTRAINT)
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = ValidationMessage.PHONE_PATTERN_CONSTRAINT )
    private String phone;

    @Email
    @NotNull
    private String email;

    private Long designationId;

    private Long departmentId;

    private Long payrollId;

    private String panNumber;

    private String bankAccountNumber;

    private String ifsc;
}
