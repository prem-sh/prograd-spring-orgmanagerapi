package io.github.premsh.orgmanager.dto.role;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Role;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JacksonXmlRootElement(localName = "Roles")
public class RolesDto {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Role")
    private List<RoleDto> roles = new ArrayList<>();

    public RolesDto(List<Role> roles) {
        this.roles = roles.stream().map(RoleDto::new).collect(Collectors.toList());
    }
}
