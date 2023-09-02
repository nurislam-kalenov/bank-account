package com.tuumsolutions.bankaccount.common.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends TuumException {

    private final String entity;
    private final String field;
    private final String value;

    public EntityNotFoundException(String entity, String field, String value) {
        super(String.format("Could not find entity %s where %s=%s", entity, field, value));

        this.entity = entity;
        this.field = field;
        this.value = value;
    }

    public EntityNotFoundException(String message, String entity) {
        super(message);

        this.entity = entity;
        this.field = null;
        this.value = null;
    }

    public EntityNotFoundException(Class<?> entity, String field, String value) {
        this(entity.getSimpleName(), field, value);
    }

    public EntityNotFoundException(String message, Class<?> entity) {
        this(message, entity.getSimpleName());
    }
}
