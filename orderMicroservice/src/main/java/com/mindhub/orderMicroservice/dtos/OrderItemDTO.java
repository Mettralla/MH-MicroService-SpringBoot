package com.mindhub.orderMicroservice.dtos;

import com.mindhub.orderMicroservice.models.OrderItem;

public class OrderItemDTO {

    private Long id;

    private Long productId;
    private Integer quantity;

    public OrderItemDTO(OrderItem orderItem) {
        id = orderItem.getId();
        productId = orderItem.getProductId();
        quantity = orderItem.getQuantity();
    }

    public OrderItemDTO() {}

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
