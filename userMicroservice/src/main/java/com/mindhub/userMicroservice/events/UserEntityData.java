package com.mindhub.userMicroservice.events;

public record UserEntityData(
        Long id,
        String username,
        String email,
        String roles
) {}
