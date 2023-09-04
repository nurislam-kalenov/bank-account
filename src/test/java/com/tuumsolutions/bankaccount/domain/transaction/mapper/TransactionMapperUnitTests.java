package com.tuumsolutions.bankaccount.domain.transaction.mapper;


import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.transaction.api.external.model.TransactionRequest;
import com.tuumsolutions.bankaccount.domain.transaction.command.CreateTransactionCommand;
import com.tuumsolutions.bankaccount.domain.transaction.entity.Transaction;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;

class TransactionMapperUnitTests {
    private final TransactionMapper transactionMapper;

    TransactionMapperUnitTests() {
        this.transactionMapper = new TransactionMapper();
    }

    @Test
    void onCreateTransactionCommandParametersReturnsUpdateAccountAmountCommandParameters() {
        var givenParam = getCreateTransactionCommandParameter();
        var actualResult = transactionMapper.toParams(givenParam);
        assertAll(
                () -> assertThat(actualResult.getAmount()).isEqualTo(givenParam.getAmount()),
                () -> assertThat(actualResult.getUserAccountId()).isEqualTo(givenParam.getUserAccountId()),
                () -> assertThat(actualResult.getTransactionType()).isEqualTo(givenParam.getTransactionType()),
                () -> assertThat(actualResult.getCurrency()).isEqualTo(givenParam.getCurrency()));
    }

    @Test
    void onCreateTransactionCommandParametersReturnsTransactionModel() {
        var givenParam = getCreateTransactionCommandParameter();
        var givenAccountId = 2L;
        var actualResult = transactionMapper.toModel(givenAccountId,givenParam);
        assertAll(
                () -> assertThat(actualResult.getAmount()).isEqualTo(givenParam.getAmount()),
                () -> assertThat(actualResult.getAccountId()).isEqualTo(givenAccountId),
                () -> assertThat(actualResult.getTransactionType()).isEqualTo(givenParam.getTransactionType()),
                () -> assertThat(actualResult.getCurrency()).isEqualTo(givenParam.getCurrency()),
                () -> assertThat(actualResult.getDescription()).isEqualTo(givenParam.getDescription())
        );
    }

    @Test
    void onCreateTransactionCommandParametersReturnsCreateTransactionCommandResult() {
        var givenParam = getCreateTransactionCommandParameter();
        var givenTransactionId = 99L;
        var givenAmountAfterTransaction = BigDecimal.TEN;
        var actualResult = transactionMapper.toResult(givenParam, givenTransactionId, givenAmountAfterTransaction);
        assertAll(
                () -> assertThat(actualResult.getAmount()).isEqualTo(givenParam.getAmount()),
                () -> assertThat(actualResult.getUserAccountId()).isEqualTo(givenParam.getUserAccountId()),
                () -> assertThat(actualResult.getTransactionType()).isEqualTo(givenParam.getTransactionType()),
                () -> assertThat(actualResult.getCurrency()).isEqualTo(givenParam.getCurrency()),
                () -> assertThat(actualResult.getDescription()).isEqualTo(givenParam.getDescription()),
                () -> assertThat(actualResult.getAmountAfterTransaction()).isEqualTo(givenAmountAfterTransaction),
                () -> assertThat(actualResult.getTransactionId()).isEqualTo(givenTransactionId)
        );
    }

    @Test
    void onTransactionRequestParamsReturnsCreateTransactionCommandParameters() {
        var givenParam = getTransactionRequest();
        var actualResult = transactionMapper.toParams(givenParam);
        assertAll(
                () -> assertThat(actualResult.getAmount()).isEqualTo(givenParam.getAmount()),
                () -> assertThat(actualResult.getUserAccountId()).isEqualTo(givenParam.getAccountId()),
                () -> assertThat(actualResult.getTransactionType()).isEqualTo(givenParam.getTransactionType()),
                () -> assertThat(actualResult.getCurrency()).isEqualTo(givenParam.getCurrency()),
                () -> assertThat(actualResult.getDescription()).isEqualTo(givenParam.getDescription())
        );
    }

    @Test
    void onCreateTransactionCommandResultReturnsCreatedTransactionResponse() {
        var givenParam = getTransactionRequest();
        var actualResult = transactionMapper.toParams(givenParam);
        assertAll(
                () -> assertThat(actualResult.getAmount()).isEqualTo(givenParam.getAmount()),
                () -> assertThat(actualResult.getUserAccountId()).isEqualTo(givenParam.getAccountId()),
                () -> assertThat(actualResult.getTransactionType()).isEqualTo(givenParam.getTransactionType()),
                () -> assertThat(actualResult.getCurrency()).isEqualTo(givenParam.getCurrency()),
                () -> assertThat(actualResult.getDescription()).isEqualTo(givenParam.getDescription())
        );
    }

    @Test
    void onCreateTransactionCommandResultReturnsCreateTransactionResponse() {
        var givenParam = getCreateTransactionCommandResult();
        var actualResult = transactionMapper.toResponse(givenParam);
        assertAll(
                () -> assertThat(actualResult.getAmount()).isEqualTo(givenParam.getAmount()),
                () -> assertThat(actualResult.getAccountId()).isEqualTo(givenParam.getUserAccountId()),
                () -> assertThat(actualResult.getTransactionType()).isEqualTo(givenParam.getTransactionType()),
                () -> assertThat(actualResult.getCurrency()).isEqualTo(givenParam.getCurrency()),
                () -> assertThat(actualResult.getDescription()).isEqualTo(givenParam.getDescription()),
                () -> assertThat(actualResult.getTransactionId()).isEqualTo(givenParam.getTransactionId()),
                () -> assertThat(actualResult.getAmountAfterTransaction()).isEqualTo(givenParam.getAmountAfterTransaction())
        );
    }

    @Test
    void onTransactionReturnsTransactionResponse() {
        var givenParam = getTransaction();
        var givenAccountId = 3L;
        var actualResult = transactionMapper.toResponse(List.of(givenParam), givenAccountId);
        assertAll(
                () -> assertThat(actualResult.get(0).getAmount()).isEqualTo(givenParam.getAmount()),
                () -> assertThat(actualResult.get(0).getAccountId()).isEqualTo(givenAccountId),
                () -> assertThat(actualResult.get(0).getTransactionType()).isEqualTo(givenParam.getTransactionType()),
                () -> assertThat(actualResult.get(0).getCurrency()).isEqualTo(givenParam.getCurrency()),
                () -> assertThat(actualResult.get(0).getDescription()).isEqualTo(givenParam.getDescription()),
                () -> assertThat(actualResult.get(0).getTransactionId()).isEqualTo(givenParam.getId())
        );
    }

    @Test
    void onCreateTransactionCommandParametersReturnsTransactionMessageModel() {
        var givenParam = getCreateTransactionCommandParameter();
        var givenTransactionId = 3L;
        var actualResult = transactionMapper.toMessage(givenTransactionId, givenParam);
        assertAll(
                () -> assertThat(actualResult.getAmount()).isEqualTo(givenParam.getAmount()),
                () -> assertThat(actualResult.getAccountId()).isEqualTo(givenParam.getUserAccountId()),
                () -> assertThat(actualResult.getTransactionType()).isEqualTo(givenParam.getTransactionType()),
                () -> assertThat(actualResult.getCurrency()).isEqualTo(givenParam.getCurrency()),
                () -> assertThat(actualResult.getDescription()).isEqualTo(givenParam.getDescription()),
                () -> assertThat(actualResult.getId()).isEqualTo(givenTransactionId)
        );
    }

    private Transaction getTransaction() {
        return Transaction.builder()
                .transactionType(TransactionType.IN)
                .accountId(1L)
                .currency(Currency.EUR)
                .amount(BigDecimal.ONE)
                .description("test")
                .id(2L)
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

    private TransactionRequest getTransactionRequest() {
        return TransactionRequest.builder()
                .transactionType(TransactionType.IN)
                .accountId(99L)
                .amount(BigDecimal.ONE)
                .currency(Currency.EUR)
                .description("test")
                .build();
    }

    private CreateTransactionCommand.Result getCreateTransactionCommandResult() {
        return CreateTransactionCommand.Result.builder()
                .transactionType(TransactionType.IN)
                .userAccountId(99L)
                .amount(BigDecimal.ONE)
                .currency(Currency.EUR)
                .description("test")
                .transactionId(1L)
                .amountAfterTransaction(BigDecimal.TEN)
                .build();
    }
}
