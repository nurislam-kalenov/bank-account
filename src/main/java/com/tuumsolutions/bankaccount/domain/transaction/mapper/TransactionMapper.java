package com.tuumsolutions.bankaccount.domain.transaction.mapper;

import com.tuumsolutions.bankaccount.domain.account.command.UpdateAccountAmountCommand;
import com.tuumsolutions.bankaccount.domain.transaction.api.external.model.TransactionRequest;
import com.tuumsolutions.bankaccount.domain.transaction.api.external.model.TransactionResponse;
import com.tuumsolutions.bankaccount.domain.transaction.command.CreateTransactionCommand;
import com.tuumsolutions.bankaccount.domain.transaction.entity.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionMapper {

    public UpdateAccountAmountCommand.Parameters toParams(CreateTransactionCommand.Parameters parameters) {
        return UpdateAccountAmountCommand.Parameters.builder()
                .amount(parameters.getAmount())
                .currency(parameters.getCurrency())
                .transactionType(parameters.getTransactionType())
                .userAccountId(parameters.getUserAccountId())
                .amount(parameters.getAmount())
                .build();
    }

    public Transaction toModel(CreateTransactionCommand.Parameters parameters) {
        return Transaction.builder()
                .accountId(parameters.getUserAccountId())
                .amount(parameters.getAmount())
                .currency(parameters.getCurrency())
                .transactionType(parameters.getTransactionType())
                .description(parameters.getDescription())
                .build();
    }

    public CreateTransactionCommand.Result toResult(CreateTransactionCommand.Parameters parameters,
                                                    Long transactionId,
                                                    BigDecimal amountAfterTransaction) {
        return CreateTransactionCommand.Result.builder()
                .transactionId(transactionId)
                .amountAfterTransaction(amountAfterTransaction)
                .userAccountId(parameters.getUserAccountId())
                .amount(parameters.getAmount())
                .currency(parameters.getCurrency())
                .transactionType(parameters.getTransactionType())
                .description(parameters.getDescription())
                .build();
    }

    public CreateTransactionCommand.Parameters toParams(TransactionRequest request) {
        return CreateTransactionCommand.Parameters.builder()
                .userAccountId(request.getAccountId())
                .amount(request.getAmount())
                .transactionType(request.getTransactionType())
                .currency(request.getCurrency())
                .description(request.getDescription())
                .build();
    }

    public TransactionResponse toResponse(CreateTransactionCommand.Result result) {
        return TransactionResponse.builder()
                .accountId(result.getUserAccountId())
                .transactionId(result.getTransactionId())
                .transactionType(result.getTransactionType())
                .amount(result.getAmount())
                .amountAfterTransaction(result.getAmountAfterTransaction())
                .currency(result.getCurrency())
                .description(result.getDescription())
                .build();
    }
}
