package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.payroll.PayrollDto;
import io.github.premsh.orgmanager.dto.profile.ProfileDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.user.UpdateUserDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.MemberProfile;
import io.github.premsh.orgmanager.models.User;
import io.github.premsh.orgmanager.repository.MemberProfileRepo;
import io.github.premsh.orgmanager.repository.UserRepo;
import io.github.premsh.orgmanager.services.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private MemberProfileRepo memberProfileRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping()
    public ResponseEntity<ProfileDto> getPayrollById() {
        User user = principalService.getUser();
        List<MemberProfile> memberships = memberProfileRepo.findByUserId(user.getId());
        return new ResponseEntity<>(new ProfileDto(user, memberships), HttpStatus.OK);
    }
    @PutMapping()
    public ResponseEntity<UpdatedDto> updateProfile(@Valid @RequestBody UpdateUserDto dto) {
        User subjectUser = userRepo.findById(principalService.getUser().getId()).orElseThrow(()->new EntityNotFoundException("User with id %d does not exist"));
        dto.get(subjectUser);
        subjectUser.setPassword(passwordEncoder.encode(subjectUser.getPassword()));
        subjectUser.setUpdatedBy(subjectUser.getId());
        userRepo.save(subjectUser);
        return new ResponseEntity<>(new UpdatedDto("Your details successfully updated"), HttpStatus.ACCEPTED);
    }
    @DeleteMapping()
    public ResponseEntity<DeletedDto> delete() {
        User subjectUser = userRepo.findById(principalService.getUser().getId()).orElseThrow(()->new EntityNotFoundException("User with id %d does not exist"));
        subjectUser.setIsDeleted(true);
        subjectUser.setDeletedBy(subjectUser.getId());
        subjectUser.setDeletedAt(new Date());
        userRepo.save(subjectUser);
        return new ResponseEntity<>(new DeletedDto("Your profile deleted successfully"), HttpStatus.ACCEPTED);
    }

}
