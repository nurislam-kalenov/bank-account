package com.tuumsolutions.bankaccount.domain.transaction.model;

import com.tuumsolutions.bankaccount.common.model.Currency;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@EqualsAndHashCode
public class TransactionMessage implements Serializable {
    private Long id;
    private Long accountId;
    private Currency currency;
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;
}
