package io.github.premsh.orgmanager.dto.membership;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateMembershipDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long orgId;
    @NotNull
    private Long roleId;
}
