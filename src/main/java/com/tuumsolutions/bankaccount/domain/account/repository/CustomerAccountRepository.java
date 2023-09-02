package com.tuumsolutions.bankaccount.domain.account.repository;

import com.tuumsolutions.bankaccount.domain.account.entity.Account;
import com.tuumsolutions.bankaccount.domain.account.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CustomerAccountRepository {

    @Select("SELECT count(id) FROM user_account WHERE customer_id = #{customerId}")
    int existByCustomerId(long customerId);

    @Select("SELECT * FROM user_account WHERE id = #{id}")
    @Result(property = "accounts", column = "id",
            javaType = List.class, many = @Many(select = "findAccountByUserAccountId"))
    Optional<User> findCustomerById(long id);

    @Select("SELECT * FROM account WHERE user_id = #{userAccountId}")
    Account findAccountByUserAccountId(long userAccountId);

    @Insert("INSERT INTO user_account (customer_id, country_code) VALUES (#{customerId}, #{countryCode})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCustomer(User entity);

    @Insert("INSERT INTO account (user_account_id, currency, amount) VALUES (#{userAccountId}, #{currency}, #{amount})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAccount(Account entity);

}
