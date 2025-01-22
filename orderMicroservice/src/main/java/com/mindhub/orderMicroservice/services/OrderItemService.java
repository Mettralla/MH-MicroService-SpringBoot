package com.mindhub.orderMicroservice.services;

import com.mindhub.orderMicroservice.dtos.NewOrderItem;
import com.mindhub.orderMicroservice.dtos.NewOrderStatus;
import com.mindhub.orderMicroservice.dtos.OrderDTO;
import com.mindhub.orderMicroservice.dtos.OrderItemDTO;
import com.mindhub.orderMicroservice.exceptions.OrderNotFoundException;
import com.mindhub.orderMicroservice.models.OrderEntity;
import com.mindhub.orderMicroservice.models.OrderItem;

public interface OrderItemService {
    OrderItemDTO createOrderItem(NewOrderItem newOrderItem, Long orderId) throws OrderNotFoundException;
    OrderItem saveOrderItem(OrderItem orderItem, OrderEntity productOwner);
}
