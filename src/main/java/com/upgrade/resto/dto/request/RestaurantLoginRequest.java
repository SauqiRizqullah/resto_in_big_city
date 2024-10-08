package com.upgrade.resto.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantLoginRequest {
    private String username;
    private String password;
}
