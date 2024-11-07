package com.upgrade.resto.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private String customerName;
    private String phoneNo;

}
