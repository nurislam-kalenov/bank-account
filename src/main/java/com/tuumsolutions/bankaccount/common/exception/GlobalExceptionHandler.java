package com.tuumsolutions.bankaccount.common.exception;

import com.tuumsolutions.bankaccount.common.model.error.EntityExistErrorResponse;
import com.tuumsolutions.bankaccount.common.model.error.EntityNotFoundErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@ControllerAdvice
@Order(0)
@ConditionalOnProperty(value = "tuum.controller-advice.enabled", havingValue = "true")
public class GlobalExceptionHandler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public EntityNotFoundErrorResponse handleValidationException(
            HttpServletRequest request, HttpServletResponse response, EntityNotFoundException e
    ) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.NOT_FOUND.value());

        return new EntityNotFoundErrorResponse(e);
    }

    @ExceptionHandler(EntityExistException.class)
    @ResponseBody
    public EntityExistErrorResponse handleValidationException(
            HttpServletRequest request, HttpServletResponse response, EntityExistException e
    ) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.CONFLICT.value());

        return new EntityExistErrorResponse(e);
    }
}
