package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.User;
import io.github.premsh.orgmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PrincipalServiceImpl implements PrincipalService{
    @Autowired
    private UserRepo userRepo;
    @Override
    public User getUser() {
        return userRepo.findById(
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()
        ).orElseThrow(()->new EntityNotFoundException("User Not found"));
    }
}
