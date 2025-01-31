package com.mindhub.orderMicroservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindhub.orderMicroservice.dtos.NewOrder;
import com.mindhub.orderMicroservice.dtos.NewOrderStatus;
import com.mindhub.orderMicroservice.dtos.OrderDTO;
import com.mindhub.orderMicroservice.dtos.UserEntityData;
import com.mindhub.orderMicroservice.exceptions.OrderNotFoundException;
import com.mindhub.orderMicroservice.exceptions.UserEntityNotFoundException;
import com.mindhub.orderMicroservice.models.Status;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface OrderEntityService {
    OrderDTO createOrder(NewOrder newOrderData) throws UserEntityNotFoundException, JsonProcessingException;
    List<OrderDTO> getAllOrders() throws UserEntityNotFoundException;
    OrderDTO updateOrderStatus(Long orderId, NewOrderStatus newStatus) throws OrderNotFoundException, UserEntityNotFoundException;
    OrderDTO showOrder(Long orderId) throws OrderNotFoundException, UserEntityNotFoundException;
    void deleteOrder(Long orderId) throws OrderNotFoundException;

    // =========================== UTILS ======================
    UserEntityData getUserEntityData(Long userId) throws UserEntityNotFoundException;
}
