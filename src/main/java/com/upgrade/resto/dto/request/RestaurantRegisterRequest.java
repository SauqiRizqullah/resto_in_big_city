package com.upgrade.resto.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantRegisterRequest {
    @NotBlank(message = "Outlet data must be filled")
    private String outlet;
    @NotBlank(message = "Email data must be filled")
    private String email;
    @NotBlank(message = "Username data must be filled")
    private String username;
    @NotBlank(message = "Password data must be filled")
    private String password;
    @NotBlank(message = "City data must be filled")
    private String city;
    @NotBlank(message = "Province data must be filled")
    private String province;
    @NotBlank(message = "Contact data must be filled")
    private String contact;

}
