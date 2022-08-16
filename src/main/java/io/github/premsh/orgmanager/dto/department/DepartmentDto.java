package io.github.premsh.orgmanager.dto.department;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.metadata.Metadata;
import io.github.premsh.orgmanager.dto.organization.OrganizationCompactDto;
import io.github.premsh.orgmanager.models.Department;
import lombok.Getter;


@JacksonXmlRootElement(localName = "Department")
@Getter
public class DepartmentDto {
    @JacksonXmlProperty(isAttribute = true)
    private final Long depId;
    @JacksonXmlProperty(isAttribute = true)
    private final Boolean isDeleted;
    private final String name;
    private final OrganizationCompactDto organisation;
    private final Metadata metadata;

    public DepartmentDto(Department dep) {
        this.depId = dep.getId();
        this.organisation = new OrganizationCompactDto(dep.getOrganization());
        this.isDeleted = dep.getIsDeleted();
        this.name = dep.getDepartmentName();
        this.metadata = new Metadata(dep.getCreatedBy(), dep.getCreatedAt(), dep.getUpdatedBy(), dep.getUpdatedAt(), dep.getDeletedBy(), dep.getDeletedAt());
    }

}
