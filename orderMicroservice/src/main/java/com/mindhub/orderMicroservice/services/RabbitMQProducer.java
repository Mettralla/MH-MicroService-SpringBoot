package com.mindhub.orderMicroservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.orderMicroservice.dtos.OrderDTO;
import com.mindhub.orderMicroservice.dtos.UserEntityData;
import com.mindhub.orderMicroservice.events.OrderCreatedEvent;
import com.mindhub.orderMicroservice.models.OrderEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void sendOrder(OrderDTO orderDTO) throws JsonProcessingException {
        OrderCreatedEvent orderCreatedEvent = convertToOrderCreatedEvent(orderDTO);

        String orderJson = objectMapper.writeValueAsString(orderCreatedEvent);

        rabbitTemplate.convertAndSend("order.exchange", "order.routingkey", orderJson);
        System.out.println("Evento enviado: " + orderJson);
    }

    private OrderCreatedEvent convertToOrderCreatedEvent(OrderDTO orderDTO) {
        OrderCreatedEvent.UserData userData = new OrderCreatedEvent.UserData(
                orderDTO.getUser().id(),
                orderDTO.getUser().username(),
                orderDTO.getUser().email(),
                orderDTO.getUser().roles()
        );

        List<OrderCreatedEvent.ProductOrderDTO> productOrderDTOs = orderDTO.getProducts().stream()
                .map(orderItemDTO -> new OrderCreatedEvent.ProductOrderDTO(
                        orderItemDTO.getId(),
                        new OrderCreatedEvent.ProductOrderDTO.ProductData(
                                orderItemDTO.getProduct().id(),
                                orderItemDTO.getProduct().name(),
                                orderItemDTO.getProduct().description(),
                                orderItemDTO.getProduct().price()
                        ),
                        orderItemDTO.getQuantity()
                ))
                .collect(Collectors.toList());

        return new OrderCreatedEvent(
                orderDTO.getId(),
                userData,
                productOrderDTOs,
                orderDTO.getStatus().toString()
        );
    }

    public UserEntityData requestUserData(Long userId) throws JsonProcessingException {
        String response = (String) rabbitTemplate.convertSendAndReceive("user.request.exchange", "user.request", userId);

        return objectMapper.readValue(response, UserEntityData.class);
    }
}