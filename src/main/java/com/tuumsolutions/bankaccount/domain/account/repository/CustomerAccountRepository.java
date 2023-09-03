package com.tuumsolutions.bankaccount.domain.account.repository;

import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import com.tuumsolutions.bankaccount.common.model.Currency;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Mapper
public interface CustomerAccountRepository {

    @Select("SELECT count(id) FROM user_account WHERE customer_id = #{customerId}")
    int existByCustomerId(long customerId);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "accounts", column = "id",
                    javaType = List.class, many = @Many(select = "findAccountByUserAccountId"))
    })
    @Select("SELECT * FROM user_account WHERE id = #{id}")
    Optional<UserAccount> findUserAccountById(long id);

    @Select("SELECT * FROM account WHERE user_account_id = #{userAccountId}")
    Account findAccountByUserAccountId(long userAccountId);


    @Select("SELECT * FROM account WHERE user_account_id = #{userAccountId} and currency = #{currency}")
    Optional<Account> findAccountByUserAccountIdAndCurrency(long userAccountId, Currency currency);

    @Insert("INSERT INTO user_account (customer_id, country_code) VALUES (#{customerId}, #{countryCode})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertUserAccount(UserAccount entity);

    @Insert("INSERT INTO account (user_account_id, currency, amount) VALUES (#{userAccountId}, #{currency}, #{amount})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertAccount(Account entity);

    @UpdateProvider(type = UpdateAccountAmountSqlProvider.class, method = "optimisticUpdateAccountAmount")
    int optimisticUpdateAccountAmount(@Param("accountId") Long accountId,
                                      @Param("amount") BigDecimal amount,
                                      @Param("version") Long version,
                                      @Param("newVersion") Long newVersion);

}
