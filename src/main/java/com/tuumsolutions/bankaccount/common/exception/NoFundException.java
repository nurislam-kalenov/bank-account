package com.tuumsolutions.bankaccount.common.exception;

import lombok.Getter;

@Getter
public class NoFundException extends TuumException {

    private final String entity;
    private final String field;
    private final String value;
    private final String message;

    public NoFundException(String entity, String field, String value, String message) {
        super(String.format("not enough fund %s where %s=%s", entity, field, value));

        this.entity = entity;
        this.field = field;
        this.value = value;
        this.message = message;
    }
}
