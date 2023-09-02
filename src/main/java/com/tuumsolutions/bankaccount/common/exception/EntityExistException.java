package com.tuumsolutions.bankaccount.common.exception;

import lombok.Getter;

@Getter
public class EntityExistException extends TuumException {

    private final String entity;
    private final String field;
    private final String value;

    public EntityExistException(String entity, String field, String value) {
        super(String.format("Entity already exist %s where %s=%s", entity, field, value));

        this.entity = entity;
        this.field = field;
        this.value = value;
    }

    public EntityExistException(String message, String entity) {
        super(message);

        this.entity = entity;
        this.field = null;
        this.value = null;
    }

    public EntityExistException(Class<?> entity, String field, String value) {
        this(entity.getSimpleName(), field, value);
    }
}
