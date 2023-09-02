package com.tuumsolutions.bankaccount.domain.account.command;

import com.tuumsolutions.bankaccount.common.Command;
import com.tuumsolutions.bankaccount.common.exception.EntityExistException;
import com.tuumsolutions.bankaccount.domain.account.CustomerAccountService;
import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.User;
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
public class CreateCustomerAccountCommand
        implements Command<CreateCustomerAccountCommand.Parameters, CreateCustomerAccountCommand.Output> {

    private final CustomerAccountService customerAccountService;
    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Output execute(Parameters parameters) {
        if (customerAccountService.isCustomerExist(parameters.getCustomerId())) {
            throw new EntityExistException(User.class, "customerId", parameters.getCustomerId().toString());
        }

        long userAccountId = customerAccountService.saveUser(User.builder()
                .customerId(parameters.customerId)
                .countryCode(parameters.countryCode)
                .build());

        parameters.getCurrencies().forEach(currency ->
                customerAccountService.saveAccount(Account.builder()
                        .amount(DEFAULT_AMOUNT)
                        .currency(currency)
                        .userAccountId(userAccountId)
                        .build()));

        return toOutput(parameters, userAccountId);
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
        static class Account {
            private final Currency currency;
            private final BigDecimal amount;
        }
    }

}
