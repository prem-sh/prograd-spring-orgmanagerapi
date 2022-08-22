package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.memberprofile.CreateMemberProfileDto;
import io.github.premsh.orgmanager.dto.memberprofile.MemberProfileDto;
import io.github.premsh.orgmanager.dto.memberprofile.MemberProfilesDto;
import io.github.premsh.orgmanager.dto.memberprofile.UpdateMemberProfileDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.models.MemberProfile;
import org.springframework.http.ResponseEntity;

public interface MemberProfileService {
    ResponseEntity<MemberProfilesDto> getAllMembers(Long orgId);
    ResponseEntity<MemberProfileDto> getMemberById(Long orgId ,Long memId);
    ResponseEntity<CreatedDto> createMembership(Long orgId, CreateMemberProfileDto memDto);
    ResponseEntity<UpdatedDto> updateMembership(Long orgId, UpdateMemberProfileDto memDto, Long memId);
    ResponseEntity<DeletedDto> removeMember(Long orgId, Long memId);
    ResponseEntity<MemberProfilesDto> filterMembers(Long orgId, String filterText, Boolean byName, Boolean byEmail, Boolean byPhone, Boolean byAddress);
}
