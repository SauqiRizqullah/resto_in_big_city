package com.upgrade.resto.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantRequest {
    @NotBlank(message = "Outlet data must be filled")
    private String outlet;
    @NotBlank(message = "City data must be filled")
    private String city;
    @NotBlank(message = "Province data must be filled")
    private String province;
    @NotBlank(message = "Contact data must be filled")
    private String contact;
}
