package com.devsuperior.hruser.controllers;

import com.devsuperior.hruser.common.CustomErrorResponse;
import javassist.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionAdviceRestController {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(NotFoundException e) {
        CustomErrorResponse error = new CustomErrorResponse("NOT_FOUND_ERROR", e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus((HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<CustomErrorResponse> handleGenericNoSuchElementException(NoSuchElementException e) {
        CustomErrorResponse error = new CustomErrorResponse("NO_SUCH_ELEMENT_ERROR", e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus((HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleGenericDataIntegrityException(DataIntegrityViolationException e) {
        CustomErrorResponse error = new CustomErrorResponse("DATA_INTEGRITY_VIOLATION", e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus((HttpStatus.CONFLICT.value()));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
