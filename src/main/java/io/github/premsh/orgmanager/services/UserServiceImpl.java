package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.user.CreateUserDto;
import io.github.premsh.orgmanager.dto.user.UpdateUserDto;
import io.github.premsh.orgmanager.dto.user.UserDto;
import io.github.premsh.orgmanager.dto.user.UsersDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.User;
import io.github.premsh.orgmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PrincipalService principalService;

    @Override
    public ResponseEntity<UsersDto> getAllUsers() {
        return new ResponseEntity<>(new UsersDto(userRepo.findAll()), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<UsersDto> searchUsers(String searchText, Boolean fname, Boolean lname, Boolean email, Boolean orgname, Boolean phone) {
        return null;
    }
    @Override
    public ResponseEntity<UserDto> getUserById(Long id) {

        return new ResponseEntity<>(new UserDto(
                userRepo.findById(id).orElseThrow(
                        ()->new EntityNotFoundException(String.format("User with id %d does not exist", id))
                )),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDto> getUserByEmail(String email) {
        return new ResponseEntity<>(new UserDto(
                userRepo.findByEmail(email).orElseThrow(
                        ()->new EntityNotFoundException(String.format("User with email %s not found",email))
                )), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedDto> createUser(CreateUserDto user) {
        User newUser = user.get();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setCreatedBy(principalService.getUser());
        newUser.setUpdatedBy(principalService.getUser());
        User usr = userRepo.save(newUser);
        return new ResponseEntity<>(new CreatedDto("New User Created Successfully",String.valueOf(usr.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateUser(UpdateUserDto userDto, Long id) {
        User subjectUser = userRepo.findById(id).orElseThrow(()->new EntityNotFoundException(String.format("User with id %d does not exist", id)));
        userDto.get(subjectUser); //get updates from dto
        subjectUser.setUpdatedBy(principalService.getUser());
        userRepo.save(subjectUser);
        return new ResponseEntity<>(new UpdatedDto("User updated successfully", id.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DeletedDto> deleteUser(Long id) throws  EntityNotFoundException{
        if (!userRepo.existsById(id)) throw new EntityNotFoundException("User not found");
        User subjectUser = userRepo.findById(id).orElseThrow(()->new EntityNotFoundException(String.format("User with id %d does not exist", id)));
        subjectUser.setDeletedAt(new Date());
        subjectUser.setDeletedBy(principalService.getUser());
        subjectUser.setIsDeleted(true);
        userRepo.save(subjectUser);
        return new ResponseEntity<>(new DeletedDto("User Deleted successfully", id.toString()), HttpStatus.ACCEPTED);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElseThrow();
    }
}
