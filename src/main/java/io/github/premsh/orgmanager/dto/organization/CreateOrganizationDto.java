package io.github.premsh.orgmanager.dto.organization;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.constants.ValidationMessage;
import io.github.premsh.orgmanager.models.Organization;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.beans.Transient;

@Data
@JacksonXmlRootElement(localName = "Organization")
public class CreateOrganizationDto {
    @NotNull
    @Size(max = 50)
    @NotBlank
    private String name;

    @URL
    private String website;

    private String address;

    @NotNull
    @Size(max = 15, message = ValidationMessage.PHONE_LENGTH_CONSTRAINT)
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = ValidationMessage.PHONE_PATTERN_CONSTRAINT )
    private String phone;

    @Email
    @NotNull
    private String email;

    @Transient
    public Organization get(){
        Organization org = new Organization();
        org.setName(name);
        org.setWebsite(website);
        org.setAddress(address);
        org.setPhone(phone);
        org.setEmail(email);
        return org;
    }
}
