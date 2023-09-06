package com.tuumsolutions.bankaccount.domain.account.command;

import com.tuumsolutions.bankaccount.common.exception.EntityExistException;
import com.tuumsolutions.bankaccount.common.model.CountryCode;
import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.domain.account.mapper.AccountMapper;
import com.tuumsolutions.bankaccount.domain.account.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.tuumsolutions.bankaccount.domain.account.AccountTestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserAccountCommandUnitTest {

    @Mock
    private UserAccountService userAccountService;
    @Spy
    private AccountMapper accountMapper;
    @InjectMocks
    private CreateUserAccountCommand createUserAccountCommand;

    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO;

    @Test
    void onFullAndCorrectInputShouldReturnResult() {
        when(userAccountService.isCustomerExist(CUSTOMER_ID)).thenReturn(false);

        var givenInputUserAccount = new UserAccount();
        givenInputUserAccount.setCustomerId(CUSTOMER_ID);
        givenInputUserAccount.setCountryCode(CountryCode.EE);

        var userAccount = createUserAccount();
        when(userAccountService.saveUser(givenInputUserAccount)).thenReturn(userAccount);

        var givenParam = createCreateUserAccountCommandParameters();
        var actualResult = createUserAccountCommand.execute(givenParam);

        verify(userAccountService).saveAccount(Account.builder()
                .amount(DEFAULT_AMOUNT)
                .currency(Currency.EUR)
                .userAccountId(USER_ACCOUNT_ID)
                .build());

        assertAll(
                () -> assertThat(actualResult.getCustomerId()).isEqualTo(givenParam.getCustomerId()),
                () -> assertThat(actualResult.getCustomerId()).isEqualTo(givenParam.getCustomerId()),
                () -> assertThat(actualResult.getAccounts()).hasSize(1),
                () -> assertThat(actualResult.getAccounts().get(0).getCurrency()).isEqualTo(givenParam.getCurrencies().get(0))
        );
    }

    @Test
    void onAlreadyExistCustomerIdInputShouldThrowException() {
        var givenParam = createCreateUserAccountCommandParameters();

        when(userAccountService.isCustomerExist(CUSTOMER_ID)).thenReturn(true);

        var actualException = assertThrows(EntityExistException.class, () -> createUserAccountCommand.execute(givenParam));

        assertAll(
                () -> assertThat(actualException.getEntity()).isEqualTo(UserAccount.class.getSimpleName()),
                () -> assertThat(actualException.getField()).isEqualTo("customerId"),
                () -> assertThat(actualException.getValue()).isEqualTo(CUSTOMER_ID+""),
                () -> assertThat(actualException.getMessage()).isEqualTo("Account already exist"));
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
                .amount(DEFAULT_AMOUNT)
                .currency(Currency.EUR)
                .userAccountId(USER_ACCOUNT_ID)
                .build();
    }
}
