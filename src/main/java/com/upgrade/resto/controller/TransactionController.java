package com.upgrade.resto.controller;

import com.upgrade.resto.constant.APIUrl;
import com.upgrade.resto.dto.request.TransactionRequest;
import com.upgrade.resto.dto.response.TransactionResponse;
import com.upgrade.resto.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.TRANSACTION)
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(produces = "application/json")
    public TransactionResponse createNewTransaction (
            @RequestBody TransactionRequest transactionRequest
    ){
        return transactionService.createNewTransaction(transactionRequest);
    }

    @GetMapping(produces = "application/json")
    public List<TransactionResponse> getAllTransactions (){
        return transactionService.getAllTransactions();
    }
}
