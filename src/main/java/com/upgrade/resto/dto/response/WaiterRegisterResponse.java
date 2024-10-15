package com.upgrade.resto.dto.response;

import com.upgrade.resto.entity.Role;
import lombok.Builder;
import lombok.Generated;
import lombok.Setter;

import java.util.List;

@Setter
@Generated
@Builder
public class WaiterRegisterResponse {
    private String username;
    private List<Role> roles;
}
