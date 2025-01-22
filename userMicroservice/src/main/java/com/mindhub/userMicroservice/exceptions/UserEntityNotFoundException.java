package com.mindhub.userMicroservice.exceptions;

public class UserEntityNotFoundException extends Exception {
    public UserEntityNotFoundException(String message) {
        super(message);
    }
}
