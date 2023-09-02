package com.tuumsolutions.bankaccount.common;

import com.tuumsolutions.bankaccount.common.exception.BusinessException;

public interface Command<T, R> {
    R execute(T request) throws BusinessException;
}
