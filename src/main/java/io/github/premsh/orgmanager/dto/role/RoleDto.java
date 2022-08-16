package io.github.premsh.orgmanager.dto.role;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Role;
import lombok.Getter;

@Getter
@JacksonXmlRootElement(localName = "Role")
public class RoleDto {
    @JacksonXmlProperty(isAttribute = true)
    private final Long roleId;
    private final String roleName;

    public RoleDto(Role role) {
        this.roleId = role.getId();
        this.roleName = role.getRoleName();
    }
}
