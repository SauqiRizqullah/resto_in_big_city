package com.upgrade.resto.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class WaiterLoginResponse {
    private String username;
    private String token;
    private List<String> roles;
}
