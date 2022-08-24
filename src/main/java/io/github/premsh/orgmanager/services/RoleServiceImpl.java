package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.constants.Permissions;
import io.github.premsh.orgmanager.constants.RoleConstants;
import io.github.premsh.orgmanager.dto.AuthDto;
import io.github.premsh.orgmanager.dto.role.RoleDto;
import io.github.premsh.orgmanager.dto.role.RolesDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Role;
import io.github.premsh.orgmanager.models.User;
import io.github.premsh.orgmanager.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PrincipalService principalService;
    @Override
    public ResponseEntity<RolesDto> getAllRoles() {
        User usr = principalService.getUser();
        List<Role> roles =  roleRepo.findAll();
        if(!usr.getRoles().contains(RoleConstants.SUPERADMIN)){
            roles = roles.stream().filter(
                    (r)->{
                        if (r.getRoleName().equals(RoleConstants.SUPERADMIN)||r.getRoleName().equals(RoleConstants.SUPPORT))
                            return false;
                        else return true;
                    }
            ).collect(Collectors.toList());
        }
        return new ResponseEntity<RolesDto>(new RolesDto(roles), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RoleDto> getRoleById(Long id) {
        Role role = roleRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Role not exist"));
        return new ResponseEntity<RoleDto>(new RoleDto(role), HttpStatus.OK);
    }
}
