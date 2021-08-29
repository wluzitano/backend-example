package com.devsuperior.hrpayroll.controllers;

import com.devsuperior.hrpayroll.common.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionAdviceRestController {


    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<CustomErrorResponse> handleGenericNoSuchElementException(NoSuchElementException e) {
        CustomErrorResponse error = new CustomErrorResponse("ILLEGAL_STATE_EXCEPTION", e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus((HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
