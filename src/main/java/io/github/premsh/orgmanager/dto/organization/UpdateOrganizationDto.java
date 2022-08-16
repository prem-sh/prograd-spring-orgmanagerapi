package io.github.premsh.orgmanager.dto.organization;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.constants.ValidationMessage;
import io.github.premsh.orgmanager.models.Organization;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.beans.Transient;

@Data
@JacksonXmlRootElement(localName = "Organization")
public class UpdateOrganizationDto {
    @Size(max = 50)
    private String name;

    @URL
    private String website;

    private String address;

    @Size(max = 15, message = ValidationMessage.PHONE_LENGTH_CONSTRAINT)
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = ValidationMessage.PHONE_PATTERN_CONSTRAINT )
    @Column(name = "phone", length = 15, nullable = false)
    private String phone;

    @Email
    private String email;

    @Transient
    public void getUpdates(Organization org){
        if(name!=null) org.setName(name);
        if(website!=null) org.setWebsite(website);
        if(address!=null) org.setAddress(address);
        if(phone!=null) org.setPhone(phone);
        if(email!=null) org.setEmail(email);
    }
}
