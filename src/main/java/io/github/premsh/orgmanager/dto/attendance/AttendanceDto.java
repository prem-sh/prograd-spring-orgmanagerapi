package io.github.premsh.orgmanager.dto.attendance;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.metadata.Metadata;
import io.github.premsh.orgmanager.models.Attendance;
import io.github.premsh.orgmanager.models.Employee;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@JacksonXmlRootElement(localName = "Role")
public class AttendanceDto {
    @JacksonXmlProperty(isAttribute = true)
    private final Long id;
    @JacksonXmlProperty(isAttribute = true)
    private final Long empId;
    @JacksonXmlProperty(isAttribute = true)
    private final Boolean deleted;
    private final String employeeEmail;
    private final Date loginTime;
    private final Date logoutTime;
    private final Metadata metadata;

    public AttendanceDto(Attendance att) {
        this.id = att.getId();
        this.empId = att.getEmployee().getId();
        this.deleted = att.getIsDeleted();
        this.employeeEmail = att.getEmployee().getEmail();
        this.loginTime = att.getLoginTime();
        this.logoutTime = att.getLogoutTime();
        this.metadata = new Metadata(
                att.getCreatedBy(), att.getCreatedAt(),
                att.getUpdatedBy(), att.getUpdatedAt(),
                att.getDeletedBy(), att.getDeletedAt()
        );
    }
}
