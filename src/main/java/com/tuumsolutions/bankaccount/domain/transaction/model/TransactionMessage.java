package com.tuumsolutions.bankaccount.domain.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tuumsolutions.bankaccount.common.model.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class TransactionMessage implements Serializable {
    private Long id;
    private Long accountId;
    private Currency currency;
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;
}
