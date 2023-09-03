package com.tuumsolutions.bankaccount.domain.account.api.external.model;

import com.tuumsolutions.bankaccount.common.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountResponse {
    private Long accountId;
    private Long customerId;
    private List<Account> accounts;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Account {
        private Currency currency;
        private BigDecimal amount;
    }
}
