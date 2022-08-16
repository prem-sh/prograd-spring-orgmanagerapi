package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.user.CreateUserDto;
import io.github.premsh.orgmanager.dto.user.UpdateUserDto;
import io.github.premsh.orgmanager.dto.user.UserDto;
import io.github.premsh.orgmanager.dto.user.UsersDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    ResponseEntity<UsersDto> getAllUsers();
    ResponseEntity<UsersDto> searchUsers(String searchText, Boolean fname, Boolean lname, Boolean email, Boolean orgname, Boolean phone);
    ResponseEntity<UserDto> getUserById(Long id);
    ResponseEntity<UserDto> getUserByEmail(String email);
    ResponseEntity<CreatedDto> createUser(CreateUserDto user);
    ResponseEntity<UpdatedDto> updateUser(UpdateUserDto user, Long id);
    ResponseEntity<DeletedDto> deleteUser(Long id);

}
