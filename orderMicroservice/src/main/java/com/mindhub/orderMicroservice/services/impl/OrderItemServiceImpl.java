package com.mindhub.orderMicroservice.services.impl;

import com.mindhub.orderMicroservice.dtos.NewOrderItem;
import com.mindhub.orderMicroservice.dtos.NewOrderStatus;
import com.mindhub.orderMicroservice.dtos.OrderDTO;
import com.mindhub.orderMicroservice.dtos.OrderItemDTO;
import com.mindhub.orderMicroservice.exceptions.OrderNotFoundException;
import com.mindhub.orderMicroservice.models.OrderEntity;
import com.mindhub.orderMicroservice.models.OrderItem;
import com.mindhub.orderMicroservice.repositories.OrderEntityRepository;
import com.mindhub.orderMicroservice.repositories.OrderItemRepository;
import com.mindhub.orderMicroservice.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderEntityRepository orderEntityRepository;

    @Override
    public OrderItemDTO createOrderItem(NewOrderItem newOrderItem, Long orderId) throws OrderNotFoundException {
        OrderItem orderItem = new OrderItem(
                newOrderItem.productId(),
                newOrderItem.quantity()
        );

        OrderEntity itemOwner = orderEntityRepository.findById(orderId)
                .orElseThrow( () -> new OrderNotFoundException("Order Not Found"));

        OrderItem createdOrderItem = saveOrderItem(orderItem, itemOwner);

        return new OrderItemDTO(createdOrderItem);
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem, OrderEntity itemOwner) {
        itemOwner.addOrderItem(orderItem);
        orderItemRepository.save(orderItem);

        return orderItem;
    }
}
