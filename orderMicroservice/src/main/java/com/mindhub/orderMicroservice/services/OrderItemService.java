package com.mindhub.orderMicroservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindhub.orderMicroservice.dtos.*;
import com.mindhub.orderMicroservice.exceptions.InsufficientStockException;
import com.mindhub.orderMicroservice.exceptions.OrderNotFoundException;
import com.mindhub.orderMicroservice.exceptions.ProductNotFoundException;
import com.mindhub.orderMicroservice.models.OrderEntity;
import com.mindhub.orderMicroservice.models.OrderItem;

import java.util.Map;

public interface OrderItemService {
    OrderItemDTO createOrderItem(NewOrderItem newOrderItem, Long orderId) throws OrderNotFoundException, InsufficientStockException;
    OrderItem saveOrderItem(OrderItem orderItem, OrderEntity productOwner);

    // UTILS
    void validateStock(NewOrderItem newOrderItem, int stock) throws InsufficientStockException;
    ProductEntityEvent getProductEntity(Long productId) throws ProductNotFoundException;
    void updateProductStock(Long productId, int updatedStock);
}
