package com.tuumsolutions.bankaccount.domain.account.api.external.model;

import com.tuumsolutions.bankaccount.common.model.CountryCode;
import com.tuumsolutions.bankaccount.common.model.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class UserAccountRequest {

    @NotNull
    private final Long customerId;
    @NotNull
    private final CountryCode countryCode;
    @NotEmpty
    private final List<Currency> currencies;

}
