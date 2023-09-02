package com.tuumsolutions.bankaccount.domain.account;

import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.User;
import com.tuumsolutions.bankaccount.domain.account.repository.CustomerAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerAccountService {

    private final CustomerAccountRepository customerAccountRepository;

    public boolean isCustomerExist(long customerId) {
        return customerAccountRepository.existByCustomerId(customerId) >= 1;
    }

    public Optional<User> findCustomerById(long id) {
        return customerAccountRepository.findCustomerById(id);
    }

    public long saveUser(User user) {
        return customerAccountRepository.insertCustomer(user);
    }

    public long saveAccount(Account account) {
        return customerAccountRepository.insertAccount(account);
    }

}
