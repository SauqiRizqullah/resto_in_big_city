package com.upgrade.resto.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {
    @NotBlank(message = "Role name can't be empty")
    private String roleName;
}
