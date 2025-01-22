package com.mindhub.userMicroservice.dtos;

import com.mindhub.userMicroservice.models.RolType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewUserEntity(
        @NotNull(message = "Username cannot be null ")
        @NotEmpty(message = "Username cannot be empty")
        String username,

        @NotNull(message = "Password cannot be null")
        @NotEmpty(message = "Password cannot be empty")
        String password,

        @NotNull(message = "Email cannot be null")
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email format is invalid")
        String email
) {
}
