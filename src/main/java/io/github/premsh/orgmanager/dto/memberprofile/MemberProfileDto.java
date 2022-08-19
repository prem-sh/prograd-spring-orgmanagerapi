package io.github.premsh.orgmanager.dto.memberprofile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.department.DepartmentDto;
import io.github.premsh.orgmanager.dto.designation.DesignationDto;
import io.github.premsh.orgmanager.dto.metadata.Metadata;
import io.github.premsh.orgmanager.dto.organization.OrganizationCompactDto;
import io.github.premsh.orgmanager.dto.organization.OrganizationDto;
import io.github.premsh.orgmanager.dto.payroll.PayrollDto;
import io.github.premsh.orgmanager.dto.role.RoleDto;
import io.github.premsh.orgmanager.dto.user.UserCompactDto;
import io.github.premsh.orgmanager.dto.user.UserDto;
import io.github.premsh.orgmanager.models.MemberProfile;
import lombok.Getter;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "MemberProfile")
public class MemberProfileDto {

    @JacksonXmlProperty(isAttribute = true)
    private final Long id;
    @JacksonXmlProperty(isAttribute = true)
    private  Boolean deleted;
    private  UserCompactDto user;
    private  OrganizationCompactDto organization;
    private  RoleDto role;
    private  DesignationDto designation;
    private  DepartmentDto department;
    private  PayrollDto payroll;
    private  String panNumber;
    private  String bankAccountNumber;
    private  String ifsc;
    private  Metadata metadata;



    public MemberProfileDto(MemberProfile profile) {
        this.id = profile.getId();
        this.deleted = profile.getIsDeleted();
        this.panNumber = profile.getPanNumber();
        this.bankAccountNumber = profile.getBankAccountNumber();
        this.ifsc = profile.getIfsc();

        if(profile.getUser() != null) this.user = new UserCompactDto(profile.getUser());
        if(profile.getRole() != null) this.role = new RoleDto(profile.getRole());
        if(profile.getOrganization() != null) this.organization = new OrganizationCompactDto(profile.getOrganization());
        if(profile.getDesignation() != null) this.designation = new DesignationDto(profile.getDesignation());
        if(profile.getDepartment() != null) this.department = new DepartmentDto(profile.getDepartment());
        if(profile.getPayroll() != null) this.payroll = new PayrollDto(profile.getPayroll());

        this.metadata = new Metadata(
                profile.getCreatedBy(), profile.getCreatedAt(),
                profile.getUpdatedBy(), profile.getUpdatedAt(),
                profile.getDeletedBy(), profile.getDeletedAt()
        );
    }
}
