package com.tuumsolutions.bankaccount.domain.account;

import com.tuumsolutions.bankaccount.common.model.CountryCode;
import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.domain.account.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountTestHelper {

    private final UserAccountRepository userAccountRepository;

    public static final long CUSTOMER_ID = 99L;
    public static final long ACCOUNT_ID = 9L;
    public static final long USER_ACCOUNT_ID = 2L;


    public UserAccount insertUserAccount(UserAccount userAccount) {
        userAccountRepository.insertUserAccountManualId(userAccount);

        userAccount.getAccounts().forEach(account ->
                userAccountRepository.insertAccountManualId(Account.builder()
                        .amount(account.getAmount())
                        .currency(account.getCurrency())
                        .userAccountId(userAccount.getId())
                        .id(account.getId())
                        .build()));

        return userAccount;
    }

    public UserAccount createUserAccount(List<Account> accounts) {
        var userAccount = new UserAccount();
        userAccount.setCountryCode(CountryCode.EE);
        userAccount.setCustomerId(CUSTOMER_ID);
        userAccount.setId(USER_ACCOUNT_ID);
        userAccount.setAccounts(accounts);
        return userAccount;
    }

    public Account createAccount(Long accountId,  BigDecimal amount, Currency currency) {
        return Account.builder()
                .id(accountId)
                .amount(amount)
                .currency(currency)
                .version(1L)
                .build();
    }
}