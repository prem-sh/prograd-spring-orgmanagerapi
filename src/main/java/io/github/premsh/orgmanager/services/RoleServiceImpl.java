package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.role.RoleDto;
import io.github.premsh.orgmanager.dto.role.RolesDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Role;
import io.github.premsh.orgmanager.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public ResponseEntity<RolesDto> getAllRoles() {
        return new ResponseEntity<RolesDto>(new RolesDto(roleRepo.findAll()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RoleDto> getRoleById(Long id) {
        Role role = roleRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Role not exist"));
        return new ResponseEntity<RoleDto>(new RoleDto(role), HttpStatus.OK);
    }
}
