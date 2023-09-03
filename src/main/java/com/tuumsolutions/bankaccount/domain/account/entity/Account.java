package com.tuumsolutions.bankaccount.domain.account.entity;

import com.tuumsolutions.bankaccount.common.model.Currency;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
public class Account {
    private final Long id;
    private final Long userAccountId;
    private final Currency currency;
    private final BigDecimal amount;
    private final Long version;
}
