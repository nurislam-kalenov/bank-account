package com.tuumsolutions.bankaccount.domain.account.command;

import com.tuumsolutions.bankaccount.common.Command;
import com.tuumsolutions.bankaccount.common.exception.EntityNotFoundException;
import com.tuumsolutions.bankaccount.domain.account.service.UserAccountService;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.domain.account.mapper.AccountMapper;
import com.tuumsolutions.bankaccount.common.model.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetUserAccountCommand implements Command<Long, GetUserAccountCommand.Result> {

    private final UserAccountService userAccountService;
    private final AccountMapper accountMapper;

    @Override
    public Result execute(Long userAccountId) {
        var userAccount = userAccountService.findUserAccountById(userAccountId)
                .orElseThrow(() -> getUserAccountNotFoundException(userAccountId));
        return accountMapper.toResult(userAccount);
    }

    private EntityNotFoundException getUserAccountNotFoundException(Long accountId) {
        return new EntityNotFoundException(
                UserAccount.class.getSimpleName(),
                "id",
                accountId.toString(),
                "Account not found"
        );
    }

    @Getter
    @Builder
    public static class Result {
        private final Long customerId;
        private final Long userAccountId;
        private final List<Result.Account> accounts;

        @Getter
        @Builder
        public static class Account {
            private final Currency currency;
            private final BigDecimal amount;
        }
    }
}
