package com.tuumsolutions.bankaccount.domain.transaction;

import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.transaction.entity.Transaction;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import com.tuumsolutions.bankaccount.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TransactionTestHelper {

    private final TransactionRepository transactionRepository;

    public final Long TRANSACTION_ID_1 = 1L;
    public final Long TRANSACTION_ID_2 = 2L;

    public Transaction insert(Transaction transaction) {
        transactionRepository.insertTransactionManualId(transaction);
        return transaction;
    }

    public Transaction createTransaction(Long id, Long accountId, TransactionType transactionType) {
        return Transaction.builder()
                .id(id)
                .accountId(accountId)
                .amount(BigDecimal.ONE)
                .transactionType(transactionType)
                .currency(Currency.EUR)
                .description("test transaction")
                .build();
    }

}