package com.mindhub.orderMicroservice.dtos;

import com.mindhub.orderMicroservice.models.Status;
import jakarta.validation.constraints.NotNull;

public record NewOrder(
        @NotNull(message = "User id cannot be null")
        Long userId,

        @NotNull(message = "Status cannot be null")
        Status status
) {}
