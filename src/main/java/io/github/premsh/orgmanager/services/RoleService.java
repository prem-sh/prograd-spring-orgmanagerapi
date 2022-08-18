package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.role.RoleDto;
import io.github.premsh.orgmanager.dto.role.RolesDto;
import org.springframework.http.ResponseEntity;

public interface RoleService {
    ResponseEntity<RolesDto> getAllRoles();
    ResponseEntity<RoleDto> getRoleById(Long id);

}
