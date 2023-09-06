package com.tuumsolutions.bankaccount.domain.account.command;

import com.tuumsolutions.bankaccount.common.exception.EntityNotFoundException;
import com.tuumsolutions.bankaccount.common.exception.NoFundException;
import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.domain.account.mapper.AccountMapper;
import com.tuumsolutions.bankaccount.domain.account.service.UserAccountService;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.tuumsolutions.bankaccount.domain.account.AccountTestHelper.ACCOUNT_ID;
import static com.tuumsolutions.bankaccount.domain.account.AccountTestHelper.USER_ACCOUNT_ID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAccountAmountCommandUnitTest {

    @Mock
    private UserAccountService userAccountService;
    @Spy
    private AccountMapper accountMapper;
    @InjectMocks
    private UpdateAccountAmountCommand updateAccountAmountCommand;

    @Test
    void onFullAndCorrectInputShouldReturnResult() {
        var givenParam = getParams(BigDecimal.ONE, TransactionType.IN);
        when(userAccountService.findUserAccountByIdAndCurrency(USER_ACCOUNT_ID, Currency.EUR))
                .thenReturn(Optional.of(createAccount()));

        var actualResult = updateAccountAmountCommand.execute(givenParam);
        verify(userAccountService).optimisticUpdateAccountAmount(ACCOUNT_ID, BigDecimal.valueOf(11), 1L);

        assertAll(
                () -> assertThat(actualResult.getAmount()).isEqualTo(givenParam.getAmount()),
                () -> assertThat(actualResult.getUserAccountId()).isEqualTo(givenParam.getUserAccountId()),
                () -> assertThat(actualResult.getTransactionType()).isEqualTo(givenParam.getTransactionType()),
                () -> assertThat(actualResult.getCurrency()).isEqualTo(givenParam.getCurrency()),
                () -> assertThat(actualResult.getAmountAfterTransaction()).isEqualTo(BigDecimal.valueOf(11)),
                () -> assertThat(actualResult.getAccountId()).isEqualTo(ACCOUNT_ID)
        );
    }

    @Test
    void onMissedAccountThrowNotFoundException() {
        var givenParam = getParams(BigDecimal.ONE, TransactionType.IN);
        when(userAccountService.findUserAccountByIdAndCurrency(USER_ACCOUNT_ID, Currency.EUR))
                .thenReturn(Optional.empty());

        var actualException = assertThrows(EntityNotFoundException.class, () -> updateAccountAmountCommand.execute(givenParam));

        assertAll(
                () -> assertThat(actualException.getEntity()).isEqualTo(UserAccount.class.getSimpleName()),
                () -> assertThat(actualException.getField()).isEqualTo("id"),
                () -> assertThat(actualException.getValue()).isEqualTo(USER_ACCOUNT_ID + ""),
                () -> assertThat(actualException.getMessage()).isEqualTo("Account not found"));
    }


    @Test
    void onAmountLessOrEqualThan0tThrowNoFundException() {
        var givenParam = getParams(BigDecimal.valueOf(20), TransactionType.OUT);
        when(userAccountService.findUserAccountByIdAndCurrency(USER_ACCOUNT_ID, Currency.EUR))
                .thenReturn(Optional.of(createAccount()));

        var actualException = assertThrows(NoFundException.class, () -> updateAccountAmountCommand.execute(givenParam));

        assertAll(
                () -> assertThat(actualException.getEntity()).isEqualTo(Account.class.getSimpleName()),
                () -> assertThat(actualException.getField()).isEqualTo("id"),
                () -> assertThat(actualException.getValue()).isEqualTo(ACCOUNT_ID + ""),
                () -> assertThat(actualException.getMessage()).isEqualTo("Not enough fund"));
    }

    private UpdateAccountAmountCommand.Parameters getParams(BigDecimal amount, TransactionType transactionType) {
        return UpdateAccountAmountCommand.Parameters.builder()
                .amount(amount)
                .userAccountId(USER_ACCOUNT_ID)
                .currency(Currency.EUR)
                .transactionType(transactionType)
                .build();
    }

    private Account createAccount() {
        return Account.builder()
                .id(ACCOUNT_ID)
                .userAccountId(USER_ACCOUNT_ID)
                .currency(Currency.EUR)
                .amount(BigDecimal.TEN)
                .version(1L)
                .build();
    }
}
