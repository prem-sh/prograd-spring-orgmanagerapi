package io.github.premsh.orgmanager.dto.organization;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.constants.ValidationMessage;
import io.github.premsh.orgmanager.dto.metadata.Metadata;
import io.github.premsh.orgmanager.models.Organization;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Getter
@JacksonXmlRootElement(localName = "Organization")
public class OrganizationDto {

    @JacksonXmlProperty(isAttribute = true, localName = "orgId")
    private final long id;
    private final String name;
    private final String website;
    private final String address;
    private final String phone;
    private final String email;
    @JacksonXmlProperty(isAttribute = true, localName = "deleted")
    private final boolean isDeleted;
    private final Metadata metadata;

    public OrganizationDto(Organization org) {
        this.id = org.getId();
        this.name = org.getName();
        this.website = org.getWebsite();
        this.email = org.getEmail();
        this.address = org.getAddress();
        this.phone = org.getPhone();
        this.isDeleted = org.getIsDeleted();
        this.metadata = new Metadata(
                org.getCreatedBy(),
                org.getCreatedAt(),
                org.getUpdatedBy(),
                org.getUpdatedAt(),
                org.getDeletedBy(),
                org.getDeletedAt()
        );
    }
}
