package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.models.User;

import java.util.List;

public interface PrincipalService {
    User getUser();
    Boolean isMemberOfOrg(Long orgId);
    Boolean hasAuthority(Long orgId, List<String> auths);
}
