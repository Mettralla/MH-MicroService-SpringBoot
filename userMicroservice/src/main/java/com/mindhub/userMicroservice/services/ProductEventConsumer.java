package com.mindhub.userMicroservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.userMicroservice.events.ProductUpdatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductEventConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    private static final String EMAIL_QUEUE = "email.queue.product";

    @RabbitListener(queues = "${rabbitmq.queue.product}")
    public void receiveProductUpdatedEvent(String message) {
        try {
            ProductUpdatedEvent event = objectMapper.readValue(message, ProductUpdatedEvent.class);
            System.out.println("Evento recibido: " + event);

            rabbitMQProducer.sendUserProductUpdateEvent(event);

        } catch (Exception e) {
            System.err.println("Error al deserializar el evento: " + e.getMessage());
        }
    }
}
