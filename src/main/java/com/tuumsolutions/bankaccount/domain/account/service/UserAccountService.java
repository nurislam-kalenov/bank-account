package com.tuumsolutions.bankaccount.domain.account.service;

import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.domain.account.repository.UserAccountRepository;
import com.tuumsolutions.bankaccount.common.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

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
            System.out.println("ERROR!!@!@");
            //throw error
        }
    }

}
