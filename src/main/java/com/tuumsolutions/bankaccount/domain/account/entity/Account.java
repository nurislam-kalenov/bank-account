package com.tuumsolutions.bankaccount.domain.account.entity;

import com.tuumsolutions.bankaccount.domain.model.Currency;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
public class Account {
    private Long id;
    private Long userAccountId;
    private Currency currency;
    private BigDecimal amount;
}
