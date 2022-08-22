package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.designation.CreateDesignationDto;
import io.github.premsh.orgmanager.dto.designation.DesignationDto;
import io.github.premsh.orgmanager.dto.designation.DesignationsDto;
import io.github.premsh.orgmanager.dto.designation.UpdateDesignationDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.models.Department;
import io.github.premsh.orgmanager.models.Designation;
import io.github.premsh.orgmanager.models.Organization;
import org.springframework.http.ResponseEntity;

public interface DesignationService {
    ResponseEntity<DesignationsDto> getAllDesignation(Long orgId);
    ResponseEntity<DesignationDto> getDesignation(Long orgId, Long desId);
    ResponseEntity<CreatedDto> createDesignation(Long orgId, CreateDesignationDto desDto);
    ResponseEntity<UpdatedDto> updateDesignation(Long orgId, UpdateDesignationDto desDto, Long desId);
    ResponseEntity<DeletedDto> deleteDesignation(Long orgId, Long desId);
    ResponseEntity<DesignationsDto> filterDesignation(String searchText, Long orgId);
    Designation createDefaultDesignation(Organization org);
}
