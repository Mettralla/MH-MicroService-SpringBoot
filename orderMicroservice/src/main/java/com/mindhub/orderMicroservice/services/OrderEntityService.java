package com.mindhub.orderMicroservice.services;

import com.mindhub.orderMicroservice.dtos.NewOrder;
import com.mindhub.orderMicroservice.dtos.NewOrderStatus;
import com.mindhub.orderMicroservice.dtos.OrderDTO;
import com.mindhub.orderMicroservice.exceptions.InsufficientStockException;
import com.mindhub.orderMicroservice.exceptions.OrderNotFoundException;
import com.mindhub.orderMicroservice.models.Status;

import java.util.List;

public interface OrderEntityService {
    OrderDTO createOrder(NewOrder newOrderData) throws InsufficientStockException;
    List<OrderDTO> getAllOrders();
    OrderDTO updateOrderStatus(Long orderId, NewOrderStatus newStatus) throws OrderNotFoundException;
}
