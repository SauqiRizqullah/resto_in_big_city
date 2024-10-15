package com.upgrade.resto.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaiterRegisterRequest {
    @NotBlank(message = "Email data must be filled")
    private String email;
    @NotBlank(message = "Username data must be filled")
    private String username;
    @NotBlank(message = "Password data must be filled")
    private String password;
    @NotBlank(message = "Waiter name data must be filled")
    private String waiterName;
    @NotBlank(message = "Phone number data must be filled")
    private String phoneNumber;
}
