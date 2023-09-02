package com.tuumsolutions.bankaccount.domain.account.repository;

import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.UserAccount;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CustomerAccountRepository {

    @Select("SELECT count(id) FROM user_account WHERE customer_id = #{customerId}")
    int existByCustomerId(long customerId);

    @Select("SELECT * FROM user_account WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "accounts", column = "id",
                    javaType = List.class, many = @Many(select = "findAccountByUserAccountId"))
    })
    Optional<UserAccount> findUserAccountById(long id);

    @Select("SELECT * FROM account WHERE user_account_id = #{userAccountId}")
    Account findAccountByUserAccountId(long userAccountId);

    @Insert("INSERT INTO user_account (customer_id, country_code) VALUES (#{customerId}, #{countryCode})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertCustomer(UserAccount entity);

    @Insert("INSERT INTO account (user_account_id, currency, amount) VALUES (#{userAccountId}, #{currency}, #{amount})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertAccount(Account entity);

}
