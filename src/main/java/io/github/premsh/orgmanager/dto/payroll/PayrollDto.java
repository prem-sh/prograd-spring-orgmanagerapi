package io.github.premsh.orgmanager.dto.payroll;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.organization.OrganizationCompactDto;
import io.github.premsh.orgmanager.dto.user.UserCompactDto;
import io.github.premsh.orgmanager.models.Department;
import io.github.premsh.orgmanager.models.User;
import lombok.Getter;

import java.util.Date;

@JacksonXmlRootElement(localName = "Department")
@Getter
public class PayrollDto {
    @JacksonXmlProperty(isAttribute = true)
    private final Long depId;
    @JacksonXmlProperty(isAttribute = true)
    private final Boolean isDeleted;
    private final String name;
    private final OrganizationCompactDto organisation;
    private final Metadata metadata;

    public PayrollDto(Department dep) {
        this.depId = dep.getId();
        this.organisation = new OrganizationCompactDto(dep.getOrganization());
        this.isDeleted = dep.getIsDeleted();
        this.name = dep.getDepartmentName();
        this.metadata = new Metadata(dep.getCreatedBy(), dep.getCreatedAt(), dep.getUpdatedBy(), dep.getUpdatedAt(), dep.getDeletedBy(), dep.getDeletedAt());
    }

    @Getter
    private static class Metadata {
        private static class Action{
            UserCompactDto by;
            Date at;
            public Action(User by, Date at) {
                this.by = new UserCompactDto(by);
                this.at = at;
            }
        }
        public Metadata(User createdBy, Date createdAt, User updatedBy, Date updatedAt, User deletedBy, Date deletedAt) {
            Action creation = new Action(createdBy, createdAt);
            Action updation = new Action(updatedBy, updatedAt);
            Action deletion = new Action(deletedBy, deletedAt);
        }
    }
}
