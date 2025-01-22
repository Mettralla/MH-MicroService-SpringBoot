package com.mindhub.productMicroservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NewProduct(
        @NotBlank(message = "Name can't be blank")
        @NotNull(message = "Name can't be null")
        String name,

        @NotBlank(message = "Description can't be blank")
        @NotNull(message = "Description can't be null")
        String description,

        @NotNull(message = "Price can't be null")
        @Positive(message = "Price must be greater than 0")
        Double price,

        @NotNull(message = "Stock can't be null")
        @Positive(message = "Price must be greater than 0")
        Integer stock
) {}
