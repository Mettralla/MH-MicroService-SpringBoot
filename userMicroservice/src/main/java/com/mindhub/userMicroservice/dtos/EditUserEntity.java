package com.mindhub.userMicroservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EditUserEntity(
        @NotNull(message = "Username cannot be null ")
        @NotEmpty(message = "Username cannot be empty")
        String username,

        @NotNull(message = "Email cannot be null")
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email format is invalid")
        String email
) {
}
