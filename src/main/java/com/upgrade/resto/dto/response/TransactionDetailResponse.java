package com.upgrade.resto.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TransactionDetailResponse {
    private String trxDetailId;
    private String menuId;
    private Long menuPrice;
    private Integer qty;
}
