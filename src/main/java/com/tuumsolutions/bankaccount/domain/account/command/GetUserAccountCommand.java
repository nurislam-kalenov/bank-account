package com.tuumsolutions.bankaccount.domain.account.command;

import com.tuumsolutions.bankaccount.common.Command;
import com.tuumsolutions.bankaccount.common.exception.EntityNotFoundException;
import com.tuumsolutions.bankaccount.domain.account.UserAccountService;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.domain.model.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
@RequiredArgsConstructor
public class GetUserAccountCommand
        implements Command<Long, GetUserAccountCommand.Output> {

    private final UserAccountService userAccountService;

    @Override
    public Output execute(Long accountId) {
        var userAccount = userAccountService.findUserAccountById(accountId)
                .orElseThrow(() -> getUserNotFoundException(accountId));
        return toOutput(userAccount);
    }

    private Output toOutput(UserAccount userAccount) {
        return Output.builder()
                .customerId(userAccount.getCustomerId())
                .userAccountId(userAccount.getId())
                .accounts(userAccount.getAccounts().stream()
                        .map(currency -> Output.Account.builder()
                                .currency(currency.getCurrency())
                                .amount(currency.getAmount())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private EntityNotFoundException getUserNotFoundException(Long accountId) {
        return new EntityNotFoundException(
                UserAccount.class.getSimpleName(),
                "id",
                accountId.toString(),
                "Account not found"
        );
    }

    @Getter
    @Builder
    @ToString
    public static class Output {
        private final Long customerId;
        private final Long userAccountId;
        private final List<Output.Account> accounts;

        @Getter
        @Builder
        @ToString
        public static class Account {
            private final Currency currency;
            private final BigDecimal amount;
        }
    }
}
