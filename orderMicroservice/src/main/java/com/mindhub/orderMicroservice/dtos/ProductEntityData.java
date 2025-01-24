package com.mindhub.orderMicroservice.dtos;

public record ProductEntityData(
        Integer id,
        String name,
        String description,
        Double price
) {}
