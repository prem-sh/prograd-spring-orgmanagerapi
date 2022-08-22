package io.github.premsh.orgmanager.dto;

import lombok.Getter;

@Getter
public class AuthDto {
    private final Long userId;
    private final boolean authority;
    private final String role;

    public AuthDto(Long userId, String role, boolean hasAuthority) {
        this.userId = userId;
        this.authority = hasAuthority;
        this.role = role;
    }
}
