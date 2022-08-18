package io.github.premsh.orgmanager.dto.designation;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.metadata.Metadata;
import io.github.premsh.orgmanager.dto.organization.OrganizationCompactDto;
import io.github.premsh.orgmanager.dto.user.UserCompactDto;
import io.github.premsh.orgmanager.models.Designation;
import io.github.premsh.orgmanager.models.User;
import lombok.Getter;

import java.util.Date;

@JacksonXmlRootElement(localName = "Designation")
@Getter
public class DesignationDto {
    @JacksonXmlProperty(isAttribute = true)
    private Long disId;
    @JacksonXmlProperty(isAttribute = true)
    private Boolean isDeleted;
    private String name;
    private OrganizationCompactDto organization;
    private Metadata metadata;

    public DesignationDto(Designation dis) {
        this.disId = dis.getId();
        this.organization = new OrganizationCompactDto(dis.getOrganization());
        this.isDeleted = dis.getIsDeleted();
        this.name = dis.getDesignationName();
        this.metadata = new Metadata(dis.getCreatedBy(), dis.getCreatedAt(), dis.getUpdatedBy(), dis.getUpdatedAt(), dis.getDeletedBy(), dis.getDeletedAt());
    }
}
