package com.upgrade.resto.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaiterLoginRequest {
    @NotBlank(message = "Username data must be filled")
    private String username;
    @NotBlank(message = "Password data must be filled")
    private String password;
}
