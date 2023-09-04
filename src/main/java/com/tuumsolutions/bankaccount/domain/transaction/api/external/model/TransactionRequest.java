package com.tuumsolutions.bankaccount.domain.transaction.api.external.model;

import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@Builder
public class TransactionRequest {
    @NotNull
    private final Long accountId;
    @NotNull
    private final TransactionType transactionType;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 6, fraction = 3)
    private final BigDecimal amount;
    @NotNull
    private final Currency currency;
    @NotBlank
    private final String description;

}
