package io.github.premsh.orgmanager.dto.employee;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.constants.ValidationMessage;
import io.github.premsh.orgmanager.models.User;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JacksonXmlRootElement(localName = "Employee")
public class UpdateEmployeeDto {

    @Size(max = 50, message = ValidationMessage.FIRSTNAME_CONSTRAINT)
    private String firstName;

    @Size(max = 50, message = ValidationMessage.LASTNAME_CONSTRAINT)
    private String lastName;

    private String address;

    @Size(max = 15, message = ValidationMessage.PHONE_LENGTH_CONSTRAINT)
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = ValidationMessage.PHONE_PATTERN_CONSTRAINT )
    private String phone;

    @Pattern(regexp = "([A-Za-z]+[0-9]|[0-9]+[A-Za-z])[A-Za-z0-9]*", message = ValidationMessage.PASSWORD_PATTERN)
    @Size(min = 6, message = ValidationMessage.PASSWORD_LENGTH)
    private String password;

    private Boolean enabled;



    private String role;

    private String designation;

    private String department;

    private Long payrollId;

    private String panNumber;

    private String bankAccountNumber;

    private String ifsc;

    @Transient
    public User getUser(User user){
        if (firstName!=null) user.setFirstName(firstName);
        if (lastName!=null) user.setLastName(lastName);
        if (address!=null) user.setAddress(address);
        if (phone!=null) user.setPhone(phone);
        if (password!=null) user.setPassword(password);
        if (enabled!=null) user.setIsEnabled(enabled);
        return user;
    }
}
