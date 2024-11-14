package com.upgrade.resto.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
public class TransactionResponse {
    private String trxId;
    private String waiterId;
    private String customerId;
//    private Long totalPrice;
    private Date transDate;
    private List<TransactionDetailResponse> transactionDetailResponseList;
}
