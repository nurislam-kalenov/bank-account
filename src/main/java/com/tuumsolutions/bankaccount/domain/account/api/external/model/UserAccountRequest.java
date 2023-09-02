package com.tuumsolutions.bankaccount.domain.account.api.external.model;

import com.tuumsolutions.bankaccount.domain.model.CountryCode;
import com.tuumsolutions.bankaccount.domain.model.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class UserAccountRequest {

    @NotBlank
    private final Long customerId;
    @NotBlank
    private final CountryCode countryCode;
    @NotEmpty
    private final List<Currency> currencies;

}
