package com.upgrade.resto.service;

import com.upgrade.resto.dto.request.TransactionRequest;
import com.upgrade.resto.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse createNewTransaction (TransactionRequest transactionRequest);

    List<TransactionResponse> getAllTransactions();

}
