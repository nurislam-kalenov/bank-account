package com.tuumsolutions.bankaccount.domain.account.entity;

import com.tuumsolutions.bankaccount.domain.model.CountryCode;
import lombok.*;

import java.util.List;

@Setter
@Builder
@EqualsAndHashCode
public class User {
    private Long id;
    private Long customerId;
    private CountryCode countryCode;
    private List<Account> accounts;
}
