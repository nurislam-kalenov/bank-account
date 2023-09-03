package com.tuumsolutions.bankaccount.domain.transaction.service;

import com.tuumsolutions.bankaccount.domain.transaction.entity.Transaction;
import com.tuumsolutions.bankaccount.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction) {
        transactionRepository.insertTransaction(transaction);
        return transaction;
    }

}
