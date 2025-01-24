package com.mindhub.orderMicroservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> insufficientStockExceptionHandler(
            InsufficientStockException insufficientStockException) {
        return new ResponseEntity<>(insufficientStockException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundExceptionHandler(
            ProductNotFoundException productNotFoundException) {
        return new ResponseEntity<>(productNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> orderNotFoundExceptionHandler(
            OrderNotFoundException orderNotFoundException) {
        return new ResponseEntity<>(orderNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserEntityNotFoundException.class)
    public ResponseEntity<String> userEntityNotFoundExceptionHandler(
            UserEntityNotFoundException userEntityNotFoundException) {
        return new ResponseEntity<>(userEntityNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
