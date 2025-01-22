package com.mindhub.orderMicroservice.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NewOrderItem(
        @NotNull(message = "Product id cannot be null")
        Long productId,

        @NotNull(message = "Quantity cannot be null")
        @Positive(message = "Quantity must be greater than 0")
        Integer quantity
) {}
