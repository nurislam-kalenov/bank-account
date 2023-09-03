package com.tuumsolutions.bankaccount.common.model.error;

import com.tuumsolutions.bankaccount.common.exception.NoFundException;
import lombok.Getter;

@Getter
public class NoFundErrorResponse {

    private final String entity;

    private final String field;

    private final String value;

    private final String message;

    public NoFundErrorResponse(NoFundException e) {
        entity = e.getEntity();
        field = e.getField();
        value = e.getValue();
        message = e.getMessage();
    }
}
