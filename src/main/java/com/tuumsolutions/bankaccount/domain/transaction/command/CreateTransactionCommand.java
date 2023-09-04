package com.tuumsolutions.bankaccount.domain.transaction.command;

import com.tuumsolutions.bankaccount.common.Command;
import com.tuumsolutions.bankaccount.domain.account.command.UpdateAccountAmountCommand;
import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.transaction.events.TransactionMessageProducer;
import com.tuumsolutions.bankaccount.domain.transaction.mapper.TransactionMapper;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import com.tuumsolutions.bankaccount.domain.transaction.service.TransactionService;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateTransactionCommand
        implements Command<CreateTransactionCommand.Parameters, CreateTransactionCommand.Result> {

    private final UpdateAccountAmountCommand updateAccountAmountCommand;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final TransactionMessageProducer transactionMessageProducer;

    @Transactional
    public Result execute(Parameters parameters) {
        log.info("Transaction create by user id {}", parameters.getUserAccountId());
        var result = updateAccountAmountCommand.execute(transactionMapper.toParams(parameters));
        var transaction = transactionService.saveTransaction(transactionMapper.toModel(result.getAccountId(), parameters));

        transactionMessageProducer.sendMsg(transactionMapper.toMessage(transaction.getId(), parameters));

        return transactionMapper.toResult(parameters, transaction.getId(), result.getAmountAfterTransaction());
    }

    @Getter
    @Builder
    @EqualsAndHashCode
    public static class Parameters {
        private final Long userAccountId;
        private final TransactionType transactionType;
        private final BigDecimal amount;
        private final Currency currency;
        private final String description;
    }

    @Getter
    @Builder
    @EqualsAndHashCode
    public static class Result {
        private final Long transactionId;
        private final Long userAccountId;
        private final TransactionType transactionType;
        private final BigDecimal amount;
        private final BigDecimal amountAfterTransaction;
        private final Currency currency;
        private final String description;
    }
}
