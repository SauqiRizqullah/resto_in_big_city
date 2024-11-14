package com.upgrade.resto.service.impl;

import com.upgrade.resto.dto.request.TransactionRequest;
import com.upgrade.resto.dto.response.TransactionDetailResponse;
import com.upgrade.resto.dto.response.TransactionResponse;
import com.upgrade.resto.entity.*;
import com.upgrade.resto.repository.TransactionRepository;
import com.upgrade.resto.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionDetailService transactionDetailService;

    private final CustomerService customerService;

    private final MenuService menuService;

    private final WaiterService waiterService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse createNewTransaction(TransactionRequest transactionRequest) {
        Customer customer = customerService.getById(transactionRequest.getCustomerId());

        Waiter waiter = waiterService.getByContext();

        Transaction trx = Transaction.builder()
                .customer(customer)
                .waiter(waiter)
                .transDate(new Date()).build();

        if (!Objects.equals(trx.getCustomer().getMembership().getMembershipName(), "NONE")){
            customerService.updatePoin(customer.getCustomerId());
        }

        Transaction savedTransaction = transactionRepository.save(trx);

        List<TransactionDetail> trxDetail = transactionRequest.getTransactionDetailRequestList().stream()
                .map(transactionDetailRequest -> {
                    Menu menu = menuService.getByIdForTransaction(transactionDetailRequest.getMenuId());

                    return TransactionDetail.builder()
                            .transaction(savedTransaction)
                            .menu(menu)
                            .menuPrice(menu.getMenuPrice())
                            .qty(transactionDetailRequest.getQty())
                            .build();

                }).toList();

        transactionDetailService.createBulk(trxDetail);
        savedTransaction.setTransactionDetails(trxDetail);

        List<TransactionDetailResponse> trxDetailResponse = trxDetail.stream().map(
                detail -> {
                    return TransactionDetailResponse.builder()
                            .trxDetailId(detail.getTrxDetailId())
                            .menuId(detail.getMenu().getMenuId())
                            .menuPrice(detail.getMenu().getMenuPrice())
                            .qty(detail.getQty())
                            .build();
                }
        ).toList();

//        Long totalPrices = 0L;
//
//        List<Long> arrayOfPrices = trxDetail.stream().map(
//                detail -> {
//                    return detail.getMenuPrice();
//                }
//        ).toList();
//
//        for (var i=0; i < trxDetail.size(); i++){
//            totalPrices += trxDetail.get(i).getMenuPrice();
//        }

//        for (var i : trxDetail.){

//        }

        return TransactionResponse.builder()
                .trxId(trx.getTrxId())
                .waiterId(savedTransaction.getWaiter().getWaiterId())
                .customerId(savedTransaction.getCustomer().getCustomerId())
                .transDate(savedTransaction.getTransDate())
                .transactionDetailResponseList(trxDetailResponse)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();


        return transactions.stream().map(trx -> {
            List<TransactionDetailResponse> trxDetailResponse = trx.getTransactionDetails().stream().map( trxDetail -> {
                return TransactionDetailResponse.builder()
                        .trxDetailId(trxDetail.getTrxDetailId())
                        .menuId(trxDetail.getMenu().getMenuId())
                        .menuPrice(trxDetail.getMenu().getMenuPrice())
                        .qty(trxDetail.getQty())
                        .build();
            }).toList();

            return TransactionResponse.builder()
                    .trxId(trx.getTrxId())
                    .waiterId(trx.getWaiter().getWaiterId())
                    .customerId(trx.getCustomer().getCustomerId())
                    .transDate(trx.getTransDate())
                    .transactionDetailResponseList(trxDetailResponse)
                    .build();
        }).toList();
    }


}
