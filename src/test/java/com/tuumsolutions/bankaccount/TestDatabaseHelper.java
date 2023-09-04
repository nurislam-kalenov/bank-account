package com.tuumsolutions.bankaccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestDatabaseHelper {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void cleanDatabase() {
        jdbcTemplate.execute("TRUNCATE TABLE account CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE transaction CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE user_account CASCADE");
    }
}
