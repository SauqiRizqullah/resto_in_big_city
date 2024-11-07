package com.upgrade.resto.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CustomerResponse {
    private String customerId;
    private String waiterId;
    private String restoId;
    private String customerName;
    private String phoneNo;
    private String membershipId;
    private Integer poin;
}
