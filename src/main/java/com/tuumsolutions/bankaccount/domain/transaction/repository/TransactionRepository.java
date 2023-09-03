package com.tuumsolutions.bankaccount.domain.transaction.repository;

import com.tuumsolutions.bankaccount.domain.transaction.entity.Transaction;
import org.apache.ibatis.annotations.*;


@Mapper
public interface TransactionRepository {

    @Insert("INSERT INTO transaction (account_id, currency, amount, description, transaction_type)" +
            " VALUES (#{accountId},#{currency}, #{amount}, #{description}, #{transactionType})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertTransaction(Transaction entity);

}
