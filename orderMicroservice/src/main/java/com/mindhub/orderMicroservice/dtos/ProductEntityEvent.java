package com.mindhub.orderMicroservice.dtos;

public record ProductEntityEvent(
        Long id,
        String name,
        String description,
        Double price,
        Integer stock
) {}
