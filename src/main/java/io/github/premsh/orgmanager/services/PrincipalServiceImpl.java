package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.constants.RoleConstants;
import io.github.premsh.orgmanager.dto.AuthDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.MemberProfile;
import io.github.premsh.orgmanager.models.User;
import io.github.premsh.orgmanager.repository.MemberProfileRepo;
import io.github.premsh.orgmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrincipalServiceImpl implements PrincipalService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MemberProfileRepo memberProfileRepo;
    @Override
    public User getUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return userRepo.findById(
                    ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()
            ).orElseThrow(()->new EntityNotFoundException("User Not found"));
        } else {
            return null;
        }
    }
    @Override
    public Boolean isMemberOfOrg(Long orgId){
        User usr = getUser();
        Long userId = usr.getId();
        System.out.println(userId);
        if (userId == null) return false;

        if (usr.getRoles().contains(RoleConstants.SUPERADMIN) || usr.getRoles().contains(RoleConstants.SUPPORT)){
            return true;
        }
        if(memberProfileRepo.findByOrgIdUserId(orgId, userId).isEmpty()){
            System.out.println("NOTFOUND");
            return false;
        }
        return true;
    }

    @Override
    public Boolean hasAuthority(Long orgId, List<String> auths) {
        User usr = getUser();
        Optional<MemberProfile> op = memberProfileRepo.findByOrgIdUserId(orgId, getUser().getId());
        if (usr.getRoles().contains(RoleConstants.SUPERADMIN) || usr.getRoles().contains(RoleConstants.SUPPORT)){
            return true;
        }
        if(op.isPresent()){
            MemberProfile memberProfile = (MemberProfile) op.get();
            return auths.contains(memberProfile.getRole().getRoleName());
        }
        return false;
    }

    public AuthDto checkAuthority(Long orgId, List<String> auths) {
        User usr = getUser();
        boolean hasPermission =  false;
        String role = null;

        Optional<MemberProfile> op = memberProfileRepo.findByOrgIdUserId(orgId, getUser().getId());

        if (usr.getRoles().contains(RoleConstants.SUPERADMIN)){
            hasPermission = true;
            role = RoleConstants.SUPPORT;
        }

        if (usr.getRoles().contains(RoleConstants.SUPERADMIN)){
            hasPermission = true;
            role = RoleConstants.SUPERADMIN;
        }

        if(op.isPresent()){
            MemberProfile memberProfile = (MemberProfile) op.get();
            hasPermission = auths.contains(memberProfile.getRole().getRoleName());
            role = memberProfile.getRole().getRoleName();
        }

        return new AuthDto(usr.getId(), role , hasPermission);
    }
}
