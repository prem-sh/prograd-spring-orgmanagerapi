package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.designation.CreateDesignationDto;
import io.github.premsh.orgmanager.dto.designation.DesignationDto;
import io.github.premsh.orgmanager.dto.designation.DesignationsDto;
import io.github.premsh.orgmanager.dto.designation.UpdateDesignationDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.services.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("org/{orgId}/designation")
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @GetMapping("/all")
    public ResponseEntity<DesignationsDto> getAllDesignation(@PathVariable Long orgId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            System.out.println(currentUserName);
            System.out.println(authentication.getPrincipal());
        }
        return designationService.getAllDesignation(orgId);
    }
    @GetMapping("/{desId}")
    public ResponseEntity<DesignationDto> getDesignation(@PathVariable Long orgId, @PathVariable Long desId) {
        return designationService.getDesignation(orgId, desId);
    }
    @PostMapping
    public ResponseEntity<CreatedDto> createDesignation(@PathVariable Long orgId, @Valid @RequestBody CreateDesignationDto desDto) {
        return designationService.createDesignation(orgId, desDto);
    }
    @PutMapping("/{desId}")
    public ResponseEntity<UpdatedDto> updateDesignation(@PathVariable Long orgId, @Valid @RequestBody UpdateDesignationDto desDto, @PathVariable Long desId) {
        return designationService.updateDesignation(orgId,desDto,desId);
    }
    @DeleteMapping("/{desId}")
    public ResponseEntity<DeletedDto> deleteDesignation(@PathVariable Long orgId, @PathVariable Long desId) {
        return designationService.deleteDesignation(orgId, desId);
    }
    @GetMapping("/filter/{searchText}")
    public ResponseEntity<DesignationsDto> filterDesignation(@PathVariable Long orgId, @PathVariable String searchText) {
        return designationService.filterDesignation(searchText, orgId);
    }
}