package com.upgrade.resto.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchMenuRequest {
    private Integer page;
    private Integer size;

    private String sortBy;
    private String direction;

    private String menuName;
}
