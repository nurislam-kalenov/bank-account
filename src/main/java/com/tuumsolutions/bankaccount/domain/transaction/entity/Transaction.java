package com.tuumsolutions.bankaccount.domain.transaction.entity;

import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
public class Transaction {
    private final Long id;
    private final Long accountId;
    private final Currency currency;
    private final BigDecimal amount;
    private final String description;
    private final TransactionType transactionType;
}
