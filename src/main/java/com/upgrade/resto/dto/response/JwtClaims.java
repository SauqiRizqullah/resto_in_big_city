package com.upgrade.resto.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class JwtClaims {
    private String userAccountId;
    private List<String> roles;

}
