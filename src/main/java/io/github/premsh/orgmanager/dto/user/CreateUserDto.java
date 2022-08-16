package io.github.premsh.orgmanager.dto.user;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.constants.ValidationMessage;
import io.github.premsh.orgmanager.models.User;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.*;

@Data
@JacksonXmlRootElement(localName = "User")
public class CreateUserDto {
    @NotNull(message = ValidationMessage.FIRSTNAME_NOTNULL)
    @NotBlank(message = ValidationMessage.FIRSTNAME_NOTBLANK)
    @Size(max = 50, message = ValidationMessage.FIRSTNAME_CONSTRAINT)
    private String firstName;

    @NotNull(message = ValidationMessage.LASTNAME_NOTNULL)
    @NotBlank(message = ValidationMessage.LASTNAME_NOTBLANK)
    @Size(max = 50, message = ValidationMessage.LASTNAME_CONSTRAINT)
    private String lastName;
    private String address;

    @NotNull(message = ValidationMessage.PHONE_NOTNULL)
    @Size(max = 15, message = ValidationMessage.PHONE_LENGTH_CONSTRAINT)
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = ValidationMessage.PHONE_PATTERN_CONSTRAINT )
    private String phone;

    @NotNull(message = ValidationMessage.EMAIL_NOTNULL)
    @Email(message = ValidationMessage.EMAIL_PATTERN)
    private String email;

    @NotNull(message = ValidationMessage.PASSWORD_NOTNULL)
    @Pattern(regexp = "([A-Za-z]+[0-9]|[0-9]+[A-Za-z])[A-Za-z0-9]*", message = ValidationMessage.PASSWORD_PATTERN)
    @Size(min = 6, message = ValidationMessage.PASSWORD_LENGTH)
    private String password;

    private Boolean enabled = true;


    @Transient
    public User get(){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(enabled);
        return user;
    }
}
