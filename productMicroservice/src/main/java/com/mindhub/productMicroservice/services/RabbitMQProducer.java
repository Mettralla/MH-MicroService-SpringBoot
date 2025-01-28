package com.mindhub.productMicroservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.productMicroservice.events.ProductUpdatedEvent;
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

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key.product}")
    private String routingKey;

    public void sendProductUpdatedEvent(ProductUpdatedEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(exchange, routingKey, eventJson);
            System.out.println("Evento enviado: " + eventJson);
        } catch (JsonProcessingException e) {
            System.err.println("Error al serializar el evento: " + e.getMessage());
        }
    }
}