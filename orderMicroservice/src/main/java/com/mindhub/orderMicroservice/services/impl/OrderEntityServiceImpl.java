package com.mindhub.orderMicroservice.services.impl;

import com.mindhub.orderMicroservice.dtos.NewOrder;
import com.mindhub.orderMicroservice.dtos.NewOrderStatus;
import com.mindhub.orderMicroservice.dtos.OrderDTO;
import com.mindhub.orderMicroservice.exceptions.InsufficientStockException;
import com.mindhub.orderMicroservice.exceptions.OrderNotFoundException;
import com.mindhub.orderMicroservice.models.OrderEntity;
import com.mindhub.orderMicroservice.models.Status;
import com.mindhub.orderMicroservice.repositories.OrderEntityRepository;
import com.mindhub.orderMicroservice.services.OrderEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderEntityServiceImpl implements OrderEntityService {

    @Autowired
    OrderEntityRepository orderRepository;

    @Override
    public OrderDTO createOrder(NewOrder newOrderData) throws InsufficientStockException {
        OrderEntity newOrder = new OrderEntity(
            newOrderData.userId(),
            newOrderData.status()
        );

        OrderEntity createdOrder = orderRepository.save(newOrder);
        return new OrderDTO(createdOrder);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                              .stream()
                              .map(order -> new OrderDTO(order))
                              .toList();
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, NewOrderStatus newStatus) throws OrderNotFoundException {
        OrderEntity preUpdateOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        preUpdateOrder.setStatus(newStatus.status());
        OrderEntity postUpdateOrder = orderRepository.save(preUpdateOrder);

        return new OrderDTO(postUpdateOrder);
    }
}
