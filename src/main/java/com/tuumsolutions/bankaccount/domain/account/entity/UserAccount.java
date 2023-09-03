package com.tuumsolutions.bankaccount.domain.account.entity;

import com.tuumsolutions.bankaccount.common.model.CountryCode;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UserAccount {
    private Long id;
    private Long customerId;
    private CountryCode countryCode;
    private List<Account> accounts;
}
