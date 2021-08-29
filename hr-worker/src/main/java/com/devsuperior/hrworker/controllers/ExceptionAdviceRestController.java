package com.devsuperior.hrworker.controllers;

import com.devsuperior.hrworker.common.CustomErrorResponse;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        error.setStatus((HttpStatus.NO_CONTENT.value()));
        return new ResponseEntity<>(error, (HttpStatus.NO_CONTENT));
    }
}
