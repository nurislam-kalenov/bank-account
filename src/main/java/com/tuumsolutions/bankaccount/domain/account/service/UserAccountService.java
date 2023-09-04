package com.tuumsolutions.bankaccount.domain.account.service;

import com.tuumsolutions.bankaccount.common.exception.InvalidOperationException;
import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.domain.account.repository.UserAccountRepository;
import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.transaction.entity.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public boolean isCustomerExist(long customerId) {
        return userAccountRepository.existByCustomerId(customerId) >= 1;
    }

    public Optional<UserAccount> findUserAccountById(long id) {
        return userAccountRepository.findUserAccountById(id);
    }

    public Optional<Account> findUserAccountByIdAndCurrency(long userAccountId, Currency currency) {
        return userAccountRepository.findAccountByUserAccountIdAndCurrency(userAccountId, currency);
    }

    public UserAccount saveUser(UserAccount userAccount) {
        userAccountRepository.insertUserAccount(userAccount);
        return userAccount;
    }

    public Account saveAccount(Account account) {
        userAccountRepository.insertAccount(account);
        return account;
    }

    public void optimisticUpdateAccountAmount(Long accountId,
                                              BigDecimal amount,
                                              Long version) {
        int rowsAffected = userAccountRepository.optimisticUpdateAccountAmount(
                accountId, amount, version, version + 1);
        if (rowsAffected == 0) {
            log.warn("Account balance update conflict,  accountId: {}", accountId);
            throw new InvalidOperationException(
                    Transaction.class.getSimpleName(),
                    "id",
                    accountId.toString(),
                    "transaction is not valid");
        }
    }

}
