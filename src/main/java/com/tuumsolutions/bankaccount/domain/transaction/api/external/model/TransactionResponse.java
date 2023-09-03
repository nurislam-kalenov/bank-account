package com.tuumsolutions.bankaccount.domain.transaction.api.external.model;

import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long transactionId;
    private Long accountId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal amountAfterTransaction;
    private Currency currency;
    private String description;
}
