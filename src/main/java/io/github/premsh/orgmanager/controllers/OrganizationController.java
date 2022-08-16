package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.organization.CreateOrganizationDto;
import io.github.premsh.orgmanager.dto.organization.OrganizationDto;
import io.github.premsh.orgmanager.dto.organization.OrganizationsDto;
import io.github.premsh.orgmanager.dto.organization.UpdateOrganizationDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/org")
public class OrganizationController {
    @Autowired
    OrganizationService organizationService;

    @GetMapping("/all")
    public ResponseEntity<OrganizationsDto> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @GetMapping("/{orgId}")
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable Long orgId) {
        return organizationService.getOrganizationById(orgId);
    }

    @PostMapping
    public ResponseEntity<CreatedDto> createOrganization(@Valid @RequestBody CreateOrganizationDto orgDto) {
        return organizationService.createOrganization(orgDto);
    }

    @PutMapping("/{orgId}")
    public ResponseEntity<UpdatedDto> updateOrganization(@Valid @RequestBody UpdateOrganizationDto orgDto, @PathVariable Long orgId) {
        return organizationService.updateOrganization(orgDto, orgId);
    }

    @DeleteMapping("/{orgId}")
    public ResponseEntity<DeletedDto> deleteOrganization(@PathVariable Long orgId) {
        return organizationService.deleteOrganization(orgId);
    }

}
