package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.organization.CreateOrganizationDto;
import io.github.premsh.orgmanager.dto.organization.OrganizationDto;
import io.github.premsh.orgmanager.dto.organization.OrganizationsDto;
import io.github.premsh.orgmanager.dto.organization.UpdateOrganizationDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import org.springframework.http.ResponseEntity;

public interface OrganizationService {
    ResponseEntity<OrganizationsDto> getAllOrganizations();
    ResponseEntity<OrganizationDto> getOrganizationById(Long id);
    ResponseEntity<CreatedDto> createOrganization(CreateOrganizationDto orgDto);
    ResponseEntity<UpdatedDto> updateOrganization(UpdateOrganizationDto orgDto, Long orgId);
    ResponseEntity<DeletedDto> deleteOrganization(Long id);
    ResponseEntity<DeletedDto> searchOrganization(Long id);
}
