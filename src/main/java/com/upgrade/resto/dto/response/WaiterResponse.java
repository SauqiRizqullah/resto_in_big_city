package com.upgrade.resto.dto.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaiterResponse {
    private String waiterId;
    private String waiterName;
}
