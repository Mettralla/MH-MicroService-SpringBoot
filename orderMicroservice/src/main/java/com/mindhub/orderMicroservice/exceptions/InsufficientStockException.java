package com.mindhub.orderMicroservice.exceptions;

public class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}
