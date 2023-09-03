package com.tuumsolutions.bankaccount.common.model.error;

import com.tuumsolutions.bankaccount.common.exception.EntityNotFoundException;
import com.tuumsolutions.bankaccount.common.exception.InvalidOperationException;
import lombok.Getter;

@Getter
public class InvalidOperationErrorResponse {

    private final String entity;

    private final String field;

    private final String value;

    private final String message;

    public InvalidOperationErrorResponse(InvalidOperationException e) {
        entity = e.getEntity();
        field = e.getField();
        value = e.getValue();
        message = e.getMessage();
    }
}
