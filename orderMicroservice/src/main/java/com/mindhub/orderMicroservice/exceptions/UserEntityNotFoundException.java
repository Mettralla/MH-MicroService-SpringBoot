package com.mindhub.orderMicroservice.exceptions;

public class UserEntityNotFoundException extends Exception {
  public UserEntityNotFoundException(String message) {
    super(message);
  }
}