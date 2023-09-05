package com.tuumsolutions.bankaccount.domain.account.mapper;

import com.tuumsolutions.bankaccount.common.model.CountryCode;
import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.account.api.external.model.UserAccountRequest;
import com.tuumsolutions.bankaccount.domain.account.command.CreateUserAccountCommand;
import com.tuumsolutions.bankaccount.domain.account.command.GetUserAccountCommand;
import com.tuumsolutions.bankaccount.domain.account.command.UpdateAccountAmountCommand;
import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.tuumsolutions.bankaccount.domain.account.AccountTestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AccountMapperUnitTest {
    private final AccountMapper accountMapper;

    AccountMapperUnitTest() {
        this.accountMapper = new AccountMapper();
    }

    @Test
    void onCreateUserAccountCommandResultToUserAccountResponse() {
        var givenResult = createGetUserAccountCommandResult();
        var actualResult = accountMapper.toResponse(givenResult);

        assertAll(
                () -> assertThat(actualResult.getAccountId()).isEqualTo(givenResult.getUserAccountId()),
                () -> assertThat(actualResult.getCustomerId()).isEqualTo(givenResult.getCustomerId()),
                () -> assertThat(actualResult.getAccounts()).hasSize(1),
                () -> assertThat(actualResult.getAccounts().get(0).getCurrency()).isEqualTo(givenResult.getAccounts().get(0).getCurrency()),
                () -> assertThat(actualResult.getAccounts().get(0).getAmount()).isEqualTo(givenResult.getAccounts().get(0).getAmount()));
    }

    @Test
    void onGetUserAccountCommandResultToUserAccountResponse() {
        var givenResult = createCreateUserAccountCommandResult();
        var actualResult = accountMapper.toResponse(givenResult);

        assertAll(
                () -> assertThat(actualResult.getAccountId()).isEqualTo(givenResult.getUserAccountId()),
                () -> assertThat(actualResult.getCustomerId()).isEqualTo(givenResult.getCustomerId()),
                () -> assertThat(actualResult.getAccounts()).hasSize(1),
                () -> assertThat(actualResult.getAccounts().get(0).getCurrency()).isEqualTo(givenResult.getAccounts().get(0).getCurrency()),
                () -> assertThat(actualResult.getAccounts().get(0).getAmount()).isEqualTo(givenResult.getAccounts().get(0).getAmount()));
    }

    @Test
    void onUserAccountRequestToCreateUserAccountCommandParameters() {
        var givenResult = createUserAccountRequest();
        var actualResult = accountMapper.toAccountCommandParameters(givenResult);

        assertAll(
                () -> assertThat(actualResult.getCustomerId()).isEqualTo(givenResult.getCustomerId()),
                () -> assertThat(actualResult.getCountryCode()).isEqualTo(givenResult.getCountryCode()),
                () -> assertThat(actualResult.getCurrencies()).hasSize(1),
                () -> assertThat(actualResult.getCurrencies().get(0)).isEqualTo(givenResult.getCurrencies().iterator().next()));
    }

    @Test
    void onUserAccountToAccountCommandParameters() {
        var givenResult = createCreateUserAccountCommandParameters();
        var actualResult = accountMapper.toUserAccount(givenResult);

        assertAll(
                () -> assertThat(actualResult.getCustomerId()).isEqualTo(givenResult.getCustomerId()),
                () -> assertThat(actualResult.getCountryCode()).isEqualTo(givenResult.getCountryCode()));
    }

    @Test
    void onCreateUserAccountCommandParametersToCreateUserAccountCommandResult() {
        var givenResult = createCreateUserAccountCommandParameters();
        var actualResult = accountMapper.toResult(givenResult, USER_ACCOUNT_ID, BigDecimal.ONE);

        assertAll(
                () -> assertThat(actualResult.getCustomerId()).isEqualTo(givenResult.getCustomerId()),
                () -> assertThat(actualResult.getUserAccountId()).isEqualTo(USER_ACCOUNT_ID),
                () -> assertThat(actualResult.getAccounts()).hasSize(1),
                () -> assertThat(actualResult.getAccounts().get(0).getAmount()).isEqualTo(actualResult.getAccounts().get(0).getAmount()),
                () -> assertThat(actualResult.getAccounts().get(0).getCurrency()).isEqualTo(actualResult.getAccounts().get(0).getCurrency())
        );
    }

    @Test
    void onUserAccountToGetUserAccountCommandResult() {
        var givenResult = createUserAccount();
        var actualResult = accountMapper.toResult(givenResult);

        assertAll(
                () -> assertThat(actualResult.getCustomerId()).isEqualTo(givenResult.getCustomerId()),
                () -> assertThat(actualResult.getUserAccountId()).isEqualTo(USER_ACCOUNT_ID),
                () -> assertThat(actualResult.getAccounts()).hasSize(1),
                () -> assertThat(actualResult.getAccounts().get(0).getAmount()).isEqualTo(actualResult.getAccounts().get(0).getAmount()),
                () -> assertThat(actualResult.getAccounts().get(0).getCurrency()).isEqualTo(actualResult.getAccounts().get(0).getCurrency())
        );
    }

    @Test
    void onUpdateAccountAmountCommandParametersToUpdateAccountAmountCommandResult() {
        var givenResult = createUpdateAccountAmountCommandParameters();
        var actualResult = accountMapper.toResult(givenResult, BigDecimal.TEN, ACCOUNT_ID);

        assertAll(
                () -> assertThat(actualResult.getAmount()).isEqualTo(givenResult.getAmount()),
                () -> assertThat(actualResult.getUserAccountId()).isEqualTo(USER_ACCOUNT_ID),
                () -> assertThat(actualResult.getAmountAfterTransaction()).isEqualTo(BigDecimal.TEN),
                () -> assertThat(actualResult.getCurrency()).isEqualTo(givenResult.getCurrency()),
                () -> assertThat(actualResult.getTransactionType()).isEqualTo(givenResult.getTransactionType()),
                () -> assertThat(actualResult.getAccountId()).isEqualTo(ACCOUNT_ID));
    }

    private CreateUserAccountCommand.Result createCreateUserAccountCommandResult() {
        return CreateUserAccountCommand.Result.builder()
                .customerId(CUSTOMER_ID)
                .userAccountId(USER_ACCOUNT_ID)
                .accounts(List.of(
                        CreateUserAccountCommand.Result.Account.builder()
                                .amount(BigDecimal.ONE)
                                .currency(Currency.EUR)
                                .build()
                )).build();
    }

    private GetUserAccountCommand.Result createGetUserAccountCommandResult() {
        return GetUserAccountCommand.Result.builder()
                .customerId(CUSTOMER_ID)
                .userAccountId(USER_ACCOUNT_ID)
                .accounts(List.of(
                        GetUserAccountCommand.Result.Account.builder()
                                .amount(BigDecimal.ONE)
                                .currency(Currency.EUR)
                                .build()
                )).build();
    }

    private UserAccountRequest createUserAccountRequest() {
        return UserAccountRequest.builder()
                .customerId(CUSTOMER_ID)
                .countryCode(CountryCode.EE)
                .currencies(Set.of(Currency.EUR))
                .build();
    }

    private CreateUserAccountCommand.Parameters createCreateUserAccountCommandParameters() {
        return CreateUserAccountCommand.Parameters.builder()
                .countryCode(CountryCode.EE)
                .customerId(CUSTOMER_ID)
                .currencies(List.of(Currency.EUR))
                .build();
    }

    private UserAccount createUserAccount() {
        var userAccount = new UserAccount();
        userAccount.setCustomerId(CUSTOMER_ID);
        userAccount.setCountryCode(CountryCode.EE);
        userAccount.setId(USER_ACCOUNT_ID);
        userAccount.setAccounts(List.of(createAccount()));

        return userAccount;
    }

    private Account createAccount() {
        return Account.builder()
                .amount(BigDecimal.ONE)
                .currency(Currency.EUR)
                .build();
    }

    private UpdateAccountAmountCommand.Parameters createUpdateAccountAmountCommandParameters() {
        return UpdateAccountAmountCommand.Parameters.builder()
                .transactionType(TransactionType.IN)
                .userAccountId(USER_ACCOUNT_ID)
                .currency(Currency.EUR)
                .amount(BigDecimal.ONE)
                .build();
    }
}
