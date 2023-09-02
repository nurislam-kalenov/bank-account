package com.tuumsolutions.bankaccount.domain.account;

import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.domain.account.repository.CustomerAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAccountService {

    private final CustomerAccountRepository customerAccountRepository;

    public boolean isCustomerExist(long customerId) {
        return customerAccountRepository.existByCustomerId(customerId) >= 1;
    }

    public Optional<UserAccount> findUserAccountById(long id) {
        return customerAccountRepository.findUserAccountById(id);
    }

    public UserAccount saveUser(UserAccount userAccount) {
         customerAccountRepository.insertCustomer(userAccount);
         return userAccount;
    }

    public Account saveAccount(Account account) {
         customerAccountRepository.insertAccount(account);
         return account;
    }

}
