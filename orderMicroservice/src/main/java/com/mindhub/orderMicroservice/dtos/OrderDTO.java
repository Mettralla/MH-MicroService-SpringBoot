package com.mindhub.orderMicroservice.dtos;

import com.mindhub.orderMicroservice.models.OrderEntity;
import com.mindhub.orderMicroservice.models.OrderItem;
import com.mindhub.orderMicroservice.models.Status;
import jakarta.persistence.*;

import java.util.List;

public class OrderDTO {
    private Long id;
    private Long userId;
    private List<OrderItemDTO> products;
    private Status status;

    public OrderDTO(OrderEntity order) {
        id = order.getId();
        userId = order.getUserId();
        status = order.getStatus();
        products = order.getProducts()
                        .stream()
                        .map( product -> new OrderItemDTO(product) )
                        .toList();
    }

    public OrderDTO() {}

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<OrderItemDTO> getProducts() {
        return products;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", products=" + products +
                ", status=" + status +
                '}';
    }
}
