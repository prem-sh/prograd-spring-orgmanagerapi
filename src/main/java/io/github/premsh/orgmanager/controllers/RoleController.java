package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.role.RoleDto;
import io.github.premsh.orgmanager.dto.role.RolesDto;
import io.github.premsh.orgmanager.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/org/{orgId}/role")
public class RoleController {

    @Autowired
    RoleService roleService;
    @GetMapping("/all")
    public ResponseEntity<RolesDto> getAllRoles() {
        return roleService.getAllRoles();
    }
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long roleId) {
        return roleService.getRoleById(roleId);
    }
}
