package com.tuumsolutions.bankaccount.domain.transaction.command;

import com.tuumsolutions.bankaccount.domain.model.Currency;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

public class CreateTransactionCommand {

    @Getter
    @Builder
    public static class Parameters {
        private Long accountId;
     //   private TransactionType transactionType;
        private BigDecimal amount;
        private Currency currency;
        private String description;
    }

    @Getter
    @Builder
    public static class Output {
        private Long accountId;
     //   private TransactionType transactionType;
        private BigDecimal amount;
        private Currency currency;
        private String description;
    }
}
