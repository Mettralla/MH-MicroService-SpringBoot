package com.mindhub.userMicroservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.userMicroservice.events.ProductUpdatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${rabbitmq.exchange.email}")
    private String emailExchange;

    @Value("${rabbitmq.routingkey.email}")
    private String emailRoutingKey;

    public void sendUserProductUpdateEvent(ProductUpdatedEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(emailExchange, emailRoutingKey, message);
            System.out.println("Evento de usuario producto actualizado: " + message);
        } catch (JsonProcessingException e) {
            System.err.println("Error al serializar el evento: " + e.getMessage());
        }
    }
}