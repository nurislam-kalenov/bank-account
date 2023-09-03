package com.tuumsolutions.bankaccount.domain.transaction.command;

import com.tuumsolutions.bankaccount.common.Command;
import com.tuumsolutions.bankaccount.domain.transaction.entity.Transaction;
import com.tuumsolutions.bankaccount.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetTransactionsByUserAccountId implements Command<Long, List<Transaction>> {
    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> execute(Long userAccountId) {
        return transactionRepository.findAllByUserAccountId(userAccountId);
    }
}
