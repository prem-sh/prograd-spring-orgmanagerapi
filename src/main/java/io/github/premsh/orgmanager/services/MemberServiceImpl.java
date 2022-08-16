package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.membership.CreateMembershipDto;
import io.github.premsh.orgmanager.dto.membership.MembershipDto;
import io.github.premsh.orgmanager.dto.membership.MembershipsDto;
import io.github.premsh.orgmanager.dto.membership.UpdateMembershipDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Membership;
import io.github.premsh.orgmanager.repository.MembershipRepo;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import io.github.premsh.orgmanager.repository.RoleRepo;
import io.github.premsh.orgmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MembershipRepo membershipRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private OrganizationRepo organizationRepo;


    @Override
    public ResponseEntity<MembershipsDto> getAllMembers() {
        return new ResponseEntity<>(new MembershipsDto(
                membershipRepo.findAll()
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MembershipDto> getMemberById(Long memId) {
        return new ResponseEntity<>(new MembershipDto(
                membershipRepo.findById(memId).orElseThrow(()->new EntityNotFoundException(
                        String.format("Membership with ID %d not found", memId)
                ))
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedDto> createMembership(CreateMembershipDto memDto) {

        Membership newMember = new Membership();
        newMember.setUser(userRepo.findById(memDto.getUserId()).orElseThrow(()->new EntityNotFoundException(
                "User with id %s not found".formatted(memDto.getUserId())
        )));
        newMember.setOrganization(organizationRepo.findById(memDto.getOrgId()).orElseThrow(()->new EntityNotFoundException(
                "Organization with id %d not found".formatted(memDto.getOrgId())
        )));
        newMember.setRole(roleRepo.findById(memDto.getRoleId()).orElseThrow(()->new EntityNotFoundException(
                "Role with id %d not found".formatted(memDto.getRoleId())
        )));
        membershipRepo.save(newMember);
        return new ResponseEntity<>(
                new CreatedDto("Membership Added successfully", String.valueOf(newMember.getId()))
                , HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<UpdatedDto> updateMembership(UpdateMembershipDto memDto, Long memId) {
        Membership subjectMember = membershipRepo.findById(memId).orElseThrow(()->new EntityNotFoundException(
                "Membership id not found"
        ));
        subjectMember.setUser(userRepo.findById(memDto.getUserId()).orElseThrow(()->new EntityNotFoundException(
                "User with id %s not found".formatted(memDto.getUserId())
        )));
        subjectMember.setOrganization(organizationRepo.findById(memDto.getOrgId()).orElseThrow(()->new EntityNotFoundException(
                "Organization with id %d not found".formatted(memDto.getOrgId())
        )));
        subjectMember.setRole(roleRepo.findById(memDto.getRoleId()).orElseThrow(()->new EntityNotFoundException(
                "Role with id %d not found".formatted(memDto.getRoleId())
        )));
        membershipRepo.save(subjectMember);

        return new ResponseEntity<>(
                new UpdatedDto("Membership Updated successfully", String.valueOf(subjectMember.getId()))
                ,HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<DeletedDto> removeMember(Long memId) {
        if(membershipRepo.existsById(memId)) membershipRepo.deleteById(memId);
        else throw new EntityNotFoundException("Membership id not found");
        return new ResponseEntity<>(new DeletedDto("Membership removed successfully", memId.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<MembershipsDto> filterMembers(String filterText, Boolean byName, Boolean byEmail, Boolean byPhone, Boolean byAddress) {
        return null;
    }
}
