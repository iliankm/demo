package com.iliankm.demo.controller;

import com.iliankm.demo.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

/**
 * Spring aware exception advice, that is applied after exception is thrown.
 */
@ControllerAdvice
class ResponseExceptionAdvice extends ResponseEntityExceptionHandler {

    /**
     * Handles all javax validation exceptions.
     *
     * @param ex the {@link ValidationException}
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles NotFoundException thrown by the service layer.
     *
     * @param ex the {@link NotFoundException}
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
