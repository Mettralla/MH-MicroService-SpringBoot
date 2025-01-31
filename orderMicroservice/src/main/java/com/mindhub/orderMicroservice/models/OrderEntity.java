package com.mindhub.orderMicroservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User id cannot be null")
    private Long userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OrderItem> products = new HashSet<>();

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    private Status status;

    public OrderEntity(Long userId, Status status) {
        this.userId = userId;
        this.status = status;
    }

    public OrderEntity() {}

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<OrderItem> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderItem> products) {
        this.products = products;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
        products.add(orderItem);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", status=" + status +
                '}';
    }
}
