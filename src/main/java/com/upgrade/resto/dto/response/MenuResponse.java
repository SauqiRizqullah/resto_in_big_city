package com.upgrade.resto.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MenuResponse {
    private String menuId;
    private String menuName;
    private Long menuPrice;
}
