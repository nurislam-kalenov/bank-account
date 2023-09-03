package com.tuumsolutions.bankaccount.domain.account.mapper;

import com.tuumsolutions.bankaccount.domain.account.api.external.model.UserAccountRequest;
import com.tuumsolutions.bankaccount.domain.account.api.external.model.UserAccountResponse;
import com.tuumsolutions.bankaccount.domain.account.command.CreateUserAccountCommand;
import com.tuumsolutions.bankaccount.domain.account.command.GetUserAccountCommand;
import com.tuumsolutions.bankaccount.domain.account.command.UpdateAccountAmountCommand;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    public UserAccountResponse toResponse(CreateUserAccountCommand.Result result) {
        return UserAccountResponse.builder()
                .accountId(result.getUserAccountId())
                .customerId(result.getCustomerId())
                .accounts(result.getAccounts().stream().map(
                        account -> UserAccountResponse.Account.builder()
                                .amount(account.getAmount())
                                .currency(account.getCurrency())
                                .build()).collect(Collectors.toList()))
                .build();
    }

    public UserAccountResponse toResponse(GetUserAccountCommand.Result result) {
        return UserAccountResponse.builder()
                .accountId(result.getUserAccountId())
                .customerId(result.getCustomerId())
                .accounts(result.getAccounts().stream().map(
                        account -> UserAccountResponse.Account.builder()
                                .amount(account.getAmount())
                                .currency(account.getCurrency())
                                .build()).collect(Collectors.toList()))
                .build();
    }

    public CreateUserAccountCommand.Parameters toAccountCommandParameters(UserAccountRequest request) {
        return CreateUserAccountCommand.Parameters.builder()
                .countryCode(request.getCountryCode())
                .customerId(request.getCustomerId())
                .currencies(request.getCurrencies())
                .build();
    }

    public UserAccount toUserAccount(CreateUserAccountCommand.Parameters parameters) {
        var model = new UserAccount();
        model.setCustomerId(parameters.getCustomerId());
        model.setCountryCode(parameters.getCountryCode());
        return model;
    }

    public CreateUserAccountCommand.Result toResult(CreateUserAccountCommand.Parameters parameters,
                                                    Long userAccountId,
                                                    BigDecimal amount) {
        return CreateUserAccountCommand.Result.builder()
                .customerId(parameters.getCustomerId())
                .userAccountId(userAccountId)
                .accounts(parameters.getCurrencies().stream()
                        .map(currency -> CreateUserAccountCommand.Result.Account.builder()
                                .currency(currency)
                                .amount(amount)
                                .build())
                        .collect(Collectors.toList())).build();
    }

    public GetUserAccountCommand.Result toResult(UserAccount userAccount) {
        return GetUserAccountCommand.Result.builder()
                .customerId(userAccount.getCustomerId())
                .userAccountId(userAccount.getId())
                .accounts(userAccount.getAccounts().stream()
                        .map(currency -> GetUserAccountCommand.Result.Account.builder()
                                .currency(currency.getCurrency())
                                .amount(currency.getAmount())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public UpdateAccountAmountCommand.Result toResult(UpdateAccountAmountCommand.Parameters parameters,
                                                      BigDecimal amountAfterTransaction) {
        return UpdateAccountAmountCommand.Result.builder()
                .amount(parameters.getAmount())
                .amountAfterTransaction(amountAfterTransaction)
                .userAccountId(parameters.getUserAccountId())
                .currency(parameters.getCurrency())
                .transactionType(parameters.getTransactionType())
                .build();
    }
}

