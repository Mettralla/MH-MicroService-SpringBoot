package com.mindhub.orderMicroservice.dtos;

import com.mindhub.orderMicroservice.models.OrderItem;

import java.util.List;

public class OrderItemDTO {

    private Long id;

    private ProductEntityData product;
    private Integer quantity;

    public OrderItemDTO(OrderItem orderItem, ProductEntityData productData) {
        id = orderItem.getId();
        product = productData;
        quantity = orderItem.getQuantity();
    }

    public OrderItemDTO() {
    }

    public ProductEntityData getProduct() {
        return product;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
