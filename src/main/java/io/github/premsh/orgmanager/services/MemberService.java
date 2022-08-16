package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.membership.CreateMembershipDto;
import io.github.premsh.orgmanager.dto.membership.MembershipDto;
import io.github.premsh.orgmanager.dto.membership.MembershipsDto;
import io.github.premsh.orgmanager.dto.membership.UpdateMembershipDto;
import io.github.premsh.orgmanager.dto.organization.CreateOrganizationDto;
import io.github.premsh.orgmanager.dto.organization.OrganizationDto;
import io.github.premsh.orgmanager.dto.organization.OrganizationsDto;
import io.github.premsh.orgmanager.dto.organization.UpdateOrganizationDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity<MembershipsDto> getAllMembers();
    ResponseEntity<MembershipDto> getMemberById(Long memId);
    ResponseEntity<CreatedDto> createMembership(CreateMembershipDto memDto);
    ResponseEntity<UpdatedDto> updateMembership(UpdateMembershipDto memDto, Long memId);
    ResponseEntity<DeletedDto> removeMember(Long memId);
    ResponseEntity<MembershipsDto> filterMembers(String filterText, Boolean byName, Boolean byEmail, Boolean byPhone, Boolean byAddress);
}
