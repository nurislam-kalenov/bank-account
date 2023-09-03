package com.tuumsolutions.bankaccount.domain.transaction.repository;

import com.tuumsolutions.bankaccount.domain.transaction.entity.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface TransactionRepository {

    @Insert("INSERT INTO transaction (account_id, currency, amount, description, transaction_type)" +
            " VALUES (#{accountId},#{currency}, #{amount}, #{description}, #{transactionType})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertTransaction(Transaction entity);

    @Select("SELECT transaction.*  FROM transaction " +
            "JOIN account a ON a.id = transaction.account_id " +
            "JOIN user_account ua ON ua.id = a.user_account_id " +
            "WHERE ua.id = #{userAccountId}")
    List<Transaction> findAllByUserAccountId(@Param("userAccountId") Long userAccountId);

}
