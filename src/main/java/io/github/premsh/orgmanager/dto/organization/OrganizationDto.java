package io.github.premsh.orgmanager.dto.organization;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.constants.ValidationMessage;
import io.github.premsh.orgmanager.models.Organization;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Getter
@JacksonXmlRootElement(localName = "Organization")
public class OrganizationDto {
    @Getter
    private static class Metadata {
        private final Date createdAt;
        private final Date updatedAt;
        private final Date deletedAt;

        public Metadata(Date createdAt, Date updatedAt, Date deletedAt) {
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.deletedAt = deletedAt;
        }
    }

    @JacksonXmlProperty(isAttribute = true, localName = "orgId")
    private final long id;
    private final String name;
    private final String website;
    private final String address;
    private final String phone;
    private final String email;
    @JacksonXmlProperty(isAttribute = true, localName = "idDeleted")
    private final boolean isDeleted;
    private final Metadata metadata;

    public OrganizationDto(Organization org) {
        this.id = org.getId();
        this.name = org.getName();
        this.website = org.getWebsite();
        this.email = org.getEmail();
        this.address = org.getAddress();
        this.phone = org.getPhone();
        this.isDeleted = org.isDeleted();
        this.metadata = new Metadata(org.getCreatedAt(), org.getUpdatedAt(), org.getDeletedAt());
    }
}
