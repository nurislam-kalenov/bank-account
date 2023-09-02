package com.tuumsolutions.bankaccount.domain.account.command;

import com.tuumsolutions.bankaccount.common.Command;
import com.tuumsolutions.bankaccount.common.exception.EntityExistException;
import com.tuumsolutions.bankaccount.domain.account.UserAccountService;
import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.domain.model.CountryCode;
import com.tuumsolutions.bankaccount.domain.model.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserAccountCommand
        implements Command<CreateUserAccountCommand.Parameters, CreateUserAccountCommand.Output> {

    private final UserAccountService userAccountService;

    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Output execute(Parameters parameters) {
        if (userAccountService.isCustomerExist(parameters.getCustomerId())) {
            throw new EntityExistException(UserAccount.class.getSimpleName(),
                    "customerId",
                    parameters.getCustomerId().toString(),
                    "Account already exist");
        }

        var userAccount = userAccountService.saveUser(toUserAccount(parameters));

        parameters.getCurrencies().forEach(currency ->
                userAccountService.saveAccount(Account.builder()
                        .amount(DEFAULT_AMOUNT)
                        .currency(currency)
                        .userAccountId(userAccount.getId())
                        .build()));

        return toOutput(parameters, userAccount.getId());
    }

    private UserAccount toUserAccount(Parameters parameters) {
        var model = new UserAccount();
        model.setCustomerId(parameters.getCustomerId());
        model.setCountryCode(parameters.getCountryCode());
        return model;
    }

    private Output toOutput(Parameters parameters, Long userAccountId) {
        return Output.builder()
                .customerId(parameters.getCustomerId())
                .userAccountId(userAccountId)
                .accounts(parameters.getCurrencies().stream()
                        .map(currency -> Output.Account.builder()
                                .currency(currency)
                                .amount(DEFAULT_AMOUNT)
                                .build())
                        .collect(Collectors.toList())).build();
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
    public static class Output {
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
