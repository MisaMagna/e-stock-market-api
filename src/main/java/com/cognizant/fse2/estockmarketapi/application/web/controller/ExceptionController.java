package com.cognizant.fse2.estockmarketapi.application.web.controller;

import com.cognizant.fse2.estockmarketapi.domain.exception.CompanyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> companyNotFoundExceptionHandler(CompanyNotFoundException exception) {
        return Map.of(
                "code", HttpStatus.NOT_FOUND.value(),
                "status", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "error", exception.getMessage()
        );
    }

    // TODO: ADD VALIDATION EXCEPTION OR CUSTOM ONE
}
