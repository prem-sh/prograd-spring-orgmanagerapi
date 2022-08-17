package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.membership.CreateMembershipDto;
import io.github.premsh.orgmanager.dto.membership.MembershipDto;
import io.github.premsh.orgmanager.dto.membership.MembershipsDto;
import io.github.premsh.orgmanager.dto.membership.UpdateMembershipDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/org/{orgId}/members")
public class MembershipController {
    @Autowired
    MemberService memberService;
    @GetMapping("/all")
    public ResponseEntity<MembershipsDto> getAllMembers(){
        return memberService.getAllMembers();
    }
    @GetMapping("{memId}")
    public ResponseEntity<MembershipDto> getMemberById(@PathVariable Long memId){
        return memberService.getMemberById(memId) ;
    }
    @PostMapping
    public ResponseEntity<CreatedDto> createMembership(@Valid @RequestBody CreateMembershipDto memDto){
        return memberService.createMembership(memDto);
    }
    @PutMapping("{memId}")
    public ResponseEntity<UpdatedDto> updateMembership(@Valid @RequestBody UpdateMembershipDto memDto, @PathVariable Long memId){
        return memberService.updateMembership(memDto, memId);
    }
    @DeleteMapping("{memId}")
    public ResponseEntity<DeletedDto> removeMember(Long memId){
        return memberService.removeMember(memId);
    }
    @GetMapping("/filter/{filterText}")
    public ResponseEntity<MembershipsDto> filterMembers(
            @PathVariable String filterText,
            @RequestParam(name = "name", defaultValue = "true") Boolean byName,
            @RequestParam(name = "email", defaultValue = "true") Boolean byEmail,
            @RequestParam(name = "phone", defaultValue = "true") Boolean byPhone,
            @RequestParam(name = "address", defaultValue = "true") Boolean byAddress
    ){
        return memberService.filterMembers(filterText, byName, byEmail, byPhone, byAddress);
    }
}
