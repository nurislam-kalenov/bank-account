package com.tuumsolutions.bankaccount.common.exception;

import lombok.Getter;

@Getter
public class FeatureNotImplementedException extends TuumException {

    private final String code;

    public FeatureNotImplementedException(String message) {
        super(message);
        this.code = null;
    }

    public FeatureNotImplementedException(String message, String code) {
        super(message);

        this.code = code;
    }
}
