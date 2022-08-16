package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.dto.user.CreateUserDto;
import io.github.premsh.orgmanager.dto.user.UpdateUserDto;
import io.github.premsh.orgmanager.dto.user.UserDto;
import io.github.premsh.orgmanager.dto.user.UsersDto;
import io.github.premsh.orgmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Email;

@Validated
@RestController
@RequestMapping(path = "/admin/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<UsersDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserByIdOrEmail(
            @Valid @RequestParam(name = "id", required = false) Long id,
            @Valid @RequestParam(name = "email", required = false) @Email(message = "Invalid email format") String email
    ){
        if(email==null && id == null) throw new ValidationException("/user path expects id or email as request parameters");
        if (email != null) return userService.getUserByEmail(email);
        return userService.getUserById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserByIdOrEmail(@RequestParam(name = "id", required = false) Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/search/{search_text}")
    public ResponseEntity<UsersDto> searchUser(
            @PathVariable(name = "search_text") String searchText,
            @RequestParam(name = "firstname", required = false, defaultValue = "true") Boolean fname,
            @RequestParam(name = "lastname", required = false, defaultValue = "true") Boolean lname,
            @RequestParam(name = "email", required = false, defaultValue = "true") Boolean email,
            @RequestParam(name = "orgname", required = false, defaultValue = "true") Boolean orgname,
            @RequestParam(name = "phone", required = false, defaultValue = "true") Boolean phone
    ){
        return userService.searchUsers(searchText, fname, lname, email, orgname, phone);
    }

    @PostMapping
    public ResponseEntity<CreatedDto> createUser(@Valid @RequestBody CreateUserDto user){
        return userService.createUser(user);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UpdatedDto> updateUser(@Valid @RequestBody UpdateUserDto user, Long id){
        return userService.updateUser(user, id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DeletedDto> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
