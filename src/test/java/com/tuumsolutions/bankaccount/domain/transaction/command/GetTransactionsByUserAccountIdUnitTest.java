package com.tuumsolutions.bankaccount.domain.transaction.command;

import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.transaction.entity.Transaction;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import com.tuumsolutions.bankaccount.domain.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTransactionsByUserAccountIdUnitTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private GetTransactionsByUserAccountId getTransactionsByUserAccountId;

    @Test
    void onFullAndCorrectInputShouldReturnResult() {
        var givenUserid = 3L;
        var givenTransactionalList = List.of(getTransaction(Currency.EUR), getTransaction(Currency.USD));
        when(transactionRepository.findAllByUserAccountId(givenUserid)).thenReturn(givenTransactionalList);

        var actualResult = getTransactionsByUserAccountId.execute(givenUserid);

        assertThat(givenTransactionalList).isEqualTo(actualResult);
    }

    private Transaction getTransaction(Currency currency) {
        return Transaction.builder()
                .transactionType(TransactionType.IN)
                .accountId(1L)
                .currency(currency)
                .amount(BigDecimal.ONE)
                .description("test")
                .id(2L)
                .build();
    }

}
