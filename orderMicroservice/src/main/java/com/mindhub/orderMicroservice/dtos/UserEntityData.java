package com.mindhub.orderMicroservice.dtos;

public record UserEntityData(
        Long id,
        String username,
        String email,
        String roles
) {}
