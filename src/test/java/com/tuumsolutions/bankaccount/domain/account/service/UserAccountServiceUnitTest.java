package com.tuumsolutions.bankaccount.domain.account.service;

import com.tuumsolutions.bankaccount.common.exception.InvalidOperationException;
import com.tuumsolutions.bankaccount.domain.account.repository.UserAccountRepository;
import com.tuumsolutions.bankaccount.domain.transaction.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.tuumsolutions.bankaccount.domain.account.AccountTestHelper.ACCOUNT_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceUnitTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private UserAccountService userAccountService;

    @Test
    void onAffectedRowZeroShouldThrowException() {
        when(userAccountRepository.optimisticUpdateAccountAmount(ACCOUNT_ID, BigDecimal.ONE, 1L, 2L))
                .thenReturn(0);

        var actualException = assertThrows(InvalidOperationException.class,
                () ->  userAccountService.optimisticUpdateAccountAmount(ACCOUNT_ID, BigDecimal.ONE, 1L));

        assertAll(
                () -> assertThat(actualException.getEntity()).isEqualTo(Transaction.class.getSimpleName()),
                () -> assertThat(actualException.getField()).isEqualTo("id"),
                () -> assertThat(actualException.getValue()).isEqualTo(ACCOUNT_ID + ""),
                () -> assertThat(actualException.getMessage()).isEqualTo("transaction is not valid"));
    }
}
