package com.tuumsolutions.bankaccount.common.exception;

public class TuumException extends RuntimeException {

    protected TuumException() {
        super();
    }

    public TuumException(String message) {
        super(message);
    }

    public TuumException(String message, Throwable cause) {
        super(message, cause);
    }
}
