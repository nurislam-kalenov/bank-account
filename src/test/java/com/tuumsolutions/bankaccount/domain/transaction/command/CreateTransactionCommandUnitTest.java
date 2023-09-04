package com.tuumsolutions.bankaccount.domain.transaction.command;

import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.account.command.UpdateAccountAmountCommand;
import com.tuumsolutions.bankaccount.domain.transaction.entity.Transaction;
import com.tuumsolutions.bankaccount.domain.transaction.events.TransactionMessageProducer;
import com.tuumsolutions.bankaccount.domain.transaction.mapper.TransactionMapper;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionMessage;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import com.tuumsolutions.bankaccount.domain.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTransactionCommandUnitTest {

    @Mock
    private UpdateAccountAmountCommand updateAccountAmountCommand;
    @Mock
    private TransactionService transactionService;
    @Mock
    private TransactionMessageProducer transactionMessageProducer;
    @Spy
    private TransactionMapper transactionMapper;
    @InjectMocks
    private CreateTransactionCommand createTransactionCommand;

    @Test
    void onFullAndCorrectInputShouldReturnResult() {
        var givenParam = getCreateTransactionCommandParameter();
        var givenTransactionId = 2L;

        when(updateAccountAmountCommand.execute(getUpdateAccountAmountCommandParameters()))
                .thenReturn(UpdateAccountAmountCommand.Result.builder().accountId(1L).amountAfterTransaction(BigDecimal.TEN).build());
        when(transactionService.saveTransaction(getTransaction(null))).thenReturn(getTransaction(givenTransactionId));
        doNothing().when(transactionMessageProducer).sendMsg(getTransactionMessage());

        var actualResult = createTransactionCommand.execute(getCreateTransactionCommandParameter());

        assertAll(
                () -> assertThat(actualResult.getAmount()).isEqualTo(givenParam.getAmount()),
                () -> assertThat(actualResult.getUserAccountId()).isEqualTo(givenParam.getUserAccountId()),
                () -> assertThat(actualResult.getTransactionType()).isEqualTo(givenParam.getTransactionType()),
                () -> assertThat(actualResult.getCurrency()).isEqualTo(givenParam.getCurrency()),
                () -> assertThat(actualResult.getDescription()).isEqualTo(givenParam.getDescription()),
                () -> assertThat(actualResult.getTransactionId()).isEqualTo(givenTransactionId)
        );

    }

    private UpdateAccountAmountCommand.Parameters getUpdateAccountAmountCommandParameters() {
        return UpdateAccountAmountCommand.Parameters.builder()
                .userAccountId(1L)
                .transactionType(TransactionType.IN)
                .amount(BigDecimal.ONE)
                .currency(Currency.EUR)
                .build();
    }

    private CreateTransactionCommand.Parameters getCreateTransactionCommandParameter() {
        return CreateTransactionCommand.Parameters.builder()
                .description("test")
                .transactionType(TransactionType.IN)
                .currency(Currency.EUR)
                .amount(BigDecimal.ONE)
                .userAccountId(1L)
                .build();
    }


    private Transaction getTransaction(Long transactionId) {
        return Transaction.builder()
                .id(transactionId)
                .transactionType(TransactionType.IN)
                .accountId(1L)
                .currency(Currency.EUR)
                .amount(BigDecimal.ONE)
                .description("test")
                .build();
    }

    private TransactionMessage getTransactionMessage() {
        var message = new TransactionMessage();
        message.setId(2L);
        message.setTransactionType(TransactionType.IN);
        message.setDescription("test");
        message.setAmount(BigDecimal.ONE);
        message.setCurrency(Currency.EUR);
        message.setAccountId(1L);
        return message;
    }
}
