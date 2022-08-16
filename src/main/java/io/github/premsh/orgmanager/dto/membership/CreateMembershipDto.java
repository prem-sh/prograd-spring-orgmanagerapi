package io.github.premsh.orgmanager.dto.membership;

import io.github.premsh.orgmanager.models.Membership;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.beans.Transient;

@Data
public class CreateMembershipDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long orgId;
    @NotNull
    private Long roleId;
}
