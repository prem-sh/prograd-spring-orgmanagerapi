package io.github.premsh.orgmanager.dto.membership;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.organization.OrganizationDto;
import io.github.premsh.orgmanager.dto.role.RoleDto;
import io.github.premsh.orgmanager.dto.user.UserDto;
import io.github.premsh.orgmanager.models.Membership;
import lombok.Getter;


@Getter
@JacksonXmlRootElement(localName = "Membership")
public class MembershipDto {

    private final UserDto member;
    private final RoleDto role;
    private final OrganizationDto organization;

    public MembershipDto(Membership membership) {
        this.member = new UserDto(membership.getUser());
        this.role = new RoleDto(membership.getRole());
        this.organization = new OrganizationDto(membership.getOrganization());
    }
}
