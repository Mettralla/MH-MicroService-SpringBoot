package com.mindhub.orderMicroservice.dtos;

import com.mindhub.orderMicroservice.models.OrderEntity;
import com.mindhub.orderMicroservice.models.OrderItem;
import com.mindhub.orderMicroservice.models.Status;
import jakarta.persistence.*;

import java.util.List;

public class OrderDTO {
    private Long id;

    private UserEntityData user;
    private List<OrderItemDTO> products;
    private Status status;

    public OrderDTO(OrderEntity order, UserEntityData userEntityData, List<OrderItemDTO> productsList) {
        id = order.getId();
        user = userEntityData;
        status = order.getStatus();
        products = productsList;
    }

    public OrderDTO() {}

    public Long getId() {
        return id;
    }

    public UserEntityData getUser() {
        return user;
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
                ", user=" + user +
                ", products=" + products +
                ", status=" + status +
                '}';
    }
}
