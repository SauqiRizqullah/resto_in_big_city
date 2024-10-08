package com.upgrade.resto.dto.response;

import com.upgrade.resto.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class RestaurantRegisterResponse {
    private String username;
    private List<Role> roles;
}
