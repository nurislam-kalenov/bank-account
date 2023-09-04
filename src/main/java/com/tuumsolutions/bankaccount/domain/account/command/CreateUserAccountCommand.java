package com.tuumsolutions.bankaccount.domain.account.command;

import com.tuumsolutions.bankaccount.common.Command;
import com.tuumsolutions.bankaccount.common.exception.EntityExistException;
import com.tuumsolutions.bankaccount.domain.account.service.UserAccountService;
import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.domain.account.mapper.AccountMapper;
import com.tuumsolutions.bankaccount.common.model.CountryCode;
import com.tuumsolutions.bankaccount.common.model.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserAccountCommand
        implements Command<CreateUserAccountCommand.Parameters, CreateUserAccountCommand.Result> {

    private final UserAccountService userAccountService;
    private final AccountMapper accountMapper;

    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Result execute(Parameters parameters) {
        log.info("create user account, customer id: {}", parameters.getCustomerId());

        if (userAccountService.isCustomerExist(parameters.getCustomerId())) {
            throw new EntityExistException(UserAccount.class.getSimpleName(),
                    "customerId",
                    parameters.getCustomerId().toString(),
                    "Account already exist");
        }

        var userAccount = userAccountService.saveUser(accountMapper.toUserAccount(parameters));

        parameters.getCurrencies().forEach(currency ->
                userAccountService.saveAccount(Account.builder()
                        .amount(DEFAULT_AMOUNT)
                        .currency(currency)
                        .userAccountId(userAccount.getId())
                        .build()));

        return accountMapper.toResult(parameters, userAccount.getId(), DEFAULT_AMOUNT);
    }

    @Getter
    @Builder
    public static class Parameters {
        private final Long customerId;
        private final CountryCode countryCode;
        private final List<Currency> currencies;
    }

    @Getter
    @Builder
    public static class Result {
        private final Long customerId;
        private final Long userAccountId;
        private final List<Account> accounts;

        @Getter
        @Builder
        public static class Account {
            private final Currency currency;
            private final BigDecimal amount;
        }
    }

}
