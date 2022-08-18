package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.memberprofile.CreateMemberProfileDto;
import io.github.premsh.orgmanager.dto.memberprofile.MemberProfileDto;
import io.github.premsh.orgmanager.dto.memberprofile.MemberProfilesDto;
import io.github.premsh.orgmanager.dto.memberprofile.UpdateMemberProfileDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.services.MemberProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("org/{orgId}/members")
public class MemberProfileController {
    @Autowired
    private MemberProfileService memberService;

    @GetMapping("/all")
    public ResponseEntity<MemberProfilesDto> getAllMembers(@PathVariable Long orgId){
        return memberService.getAllMembers(orgId);
    }
    @GetMapping("{memId}")
    public ResponseEntity<MemberProfileDto> getMemberById(@PathVariable Long orgId, @PathVariable Long memId){
        return memberService.getMemberById(orgId, memId) ;
    }
    @PostMapping
    public ResponseEntity<CreatedDto> createMembership(@PathVariable Long orgId, @Valid @RequestBody CreateMemberProfileDto memDto){
        return memberService.createMembership(orgId, memDto);
    }
    @PutMapping("{memId}")
    public ResponseEntity<UpdatedDto> updateMembership(@PathVariable Long orgId,@Valid @RequestBody UpdateMemberProfileDto memDto, @PathVariable Long memId){
        return memberService.updateMembership(orgId, memDto, memId);
    }
    @DeleteMapping("{memId}")
    public ResponseEntity<DeletedDto> removeMember(@PathVariable Long orgId, @PathVariable Long memId){
        return memberService.removeMember(orgId, memId);
    }
    @GetMapping("/filter/{filterText}")
    public ResponseEntity<MemberProfilesDto> filterMembers(
            @PathVariable Long orgId,
            @PathVariable String filterText,
            @RequestParam(name = "name", defaultValue = "true") Boolean byName,
            @RequestParam(name = "email", defaultValue = "true") Boolean byEmail,
            @RequestParam(name = "phone", defaultValue = "true") Boolean byPhone,
            @RequestParam(name = "address", defaultValue = "true") Boolean byAddress
    ){
        return memberService.filterMembers(orgId, filterText, byName, byEmail, byPhone, byAddress);
    }
}
