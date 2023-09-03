package com.tuumsolutions.bankaccount.domain.account.command;

import com.tuumsolutions.bankaccount.common.Command;
import com.tuumsolutions.bankaccount.common.exception.EntityNotFoundException;
import com.tuumsolutions.bankaccount.common.exception.NoFundException;
import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.domain.account.mapper.AccountMapper;
import com.tuumsolutions.bankaccount.domain.account.service.UserAccountService;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateAccountAmountCommand
        implements Command<UpdateAccountAmountCommand.Parameters, UpdateAccountAmountCommand.Result> {

    private final UserAccountService userAccountService;
    private final AccountMapper accountMapper;

    @Override
    public Result execute(Parameters parameters) {
        var account = userAccountService.findUserAccountByIdAndCurrency(parameters.getUserAccountId(), parameters.getCurrency())
                .orElseThrow(() -> getUserAccountNotFoundException(parameters.getUserAccountId()));

        var amountAfterTransaction = calculateAmount(parameters, account.getAmount());

        if (amountAfterTransaction.compareTo(BigDecimal.ZERO) < 0) {
            throw getNoFundException(account.getId());
        }

        userAccountService.optimisticUpdateAccountAmount(account.getId(), amountAfterTransaction, account.getVersion());

        return accountMapper.toResult(parameters, amountAfterTransaction);
    }

    private BigDecimal calculateAmount(UpdateAccountAmountCommand.Parameters request, BigDecimal accountAmount) {
        BigDecimal result;
        if (TransactionType.IN == request.getTransactionType()) {
            result = accountAmount.add(request.getAmount());
        } else {
            result = accountAmount.subtract(request.getAmount());
        }
        return result;
    }

    private EntityNotFoundException getUserAccountNotFoundException(Long userAccountId) {
        return new EntityNotFoundException(
                UserAccount.class.getSimpleName(),
                "id",
                userAccountId.toString(),
                "Account not found"
        );
    }

    private NoFundException getNoFundException(Long accountId) {
        return new NoFundException(
                Account.class.getSimpleName(),
                "id",
                accountId.toString(),
                "Not enough fund"
        );
    }

    @Getter
    @Builder
    public static class Parameters {
        private final Long userAccountId;
        private final BigDecimal amount;
        private final Currency currency;
        private final TransactionType transactionType;
    }

    @Getter
    @Builder
    public static class Result {
        private final Long userAccountId;
        private final TransactionType transactionType;
        private final BigDecimal amount;
        private final BigDecimal amountAfterTransaction;
        private final Currency currency;
    }

}
