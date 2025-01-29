package com.mindhub.emailMicroservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.emailMicroservice.events.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class OrderCreatedConsumer {
    @Autowired
    private PDFGeneratorService pdfGeneratorService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.mail.username}")
    private String emailName;


    @RabbitListener(queues = "order.queue")
    public void onOrderCreatedEvent(String eventJson) {
        try {
            OrderCreatedEvent orderCreatedEvent = objectMapper.readValue(eventJson, OrderCreatedEvent.class);

            ByteArrayInputStream pdfStream = pdfGeneratorService.generatePdfFromOrder(orderCreatedEvent);

            emailService.sendEmailWithAttachment(emailName, pdfStream, orderCreatedEvent.getUser().getUsername());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
