package com.tuumsolutions.bankaccount.common.model.error;

import com.tuumsolutions.bankaccount.common.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class EntityNotFoundErrorResponse {

    private final String entity;

    private final String field;

    private final String value;

    public EntityNotFoundErrorResponse(EntityNotFoundException e) {
        entity = e.getEntity();
        field = e.getField();
        value = e.getValue();
    }
}
