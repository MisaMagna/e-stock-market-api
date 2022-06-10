package com.cognizant.fse2.estockmarketapi.application.web.controller;

import com.cognizant.fse2.estockmarketapi.domain.exception.CompanyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    private static final String FIELD_CODE = "code";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_ERRORS = "errors";

    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> companyNotFoundExceptionHandler(CompanyNotFoundException exception) {
        return Map.of(
                FIELD_CODE, HttpStatus.NOT_FOUND.value(),
                FIELD_STATUS, HttpStatus.NOT_FOUND.getReasonPhrase(),
                FIELD_ERRORS, List.of(exception.getMessage())
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> constraintViolationExceptionHandler(ConstraintViolationException exception) {
        return Map.of(
                FIELD_CODE, HttpStatus.BAD_REQUEST.value(),
                FIELD_STATUS, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                FIELD_ERRORS, List.of(exception.getMessage().split(", "))
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> methodArgumentTypeMismatchExceptionHandler(IllegalArgumentException exception) {
        return Map.of(
                FIELD_CODE, HttpStatus.BAD_REQUEST.value(),
                FIELD_STATUS, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                FIELD_ERRORS, List.of(exception.getMessage())
        );
    }
}
