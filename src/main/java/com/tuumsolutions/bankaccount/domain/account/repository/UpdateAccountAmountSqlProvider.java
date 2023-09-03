package com.tuumsolutions.bankaccount.domain.account.repository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.math.BigDecimal;

public class UpdateAccountAmountSqlProvider {
    public String optimisticUpdateAccountAmount(@Param("accountId") Long accountId,
                                                @Param("amount") BigDecimal amount,
                                                @Param("version") Long version,
                                                @Param("newVersion") Long newVersion) {
        return new SQL() {{
            UPDATE("account");
            SET("amount = #{amount}");
            SET("version = #{newVersion}");
            WHERE("id = #{accountId}");
            WHERE("version = #{version}");
        }}.toString();
    }
}
