package com.tuumsolutions.bankaccount.common.exception;

import lombok.Getter;

@Getter
public class NotImplementedException extends TuumException {

    private final String code;

    public NotImplementedException(String message) {
        super(message);
        this.code = null;
    }

    public NotImplementedException(String message, String code) {
        super(message);

        this.code = code;
    }
}
