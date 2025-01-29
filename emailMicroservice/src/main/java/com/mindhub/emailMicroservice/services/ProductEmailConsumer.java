package com.mindhub.emailMicroservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.emailMicroservice.events.ProductUpdatedEvent;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class ProductEmailConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailName;

    @RabbitListener(queues = "${rabbitmq.queue.email}")
    public void receiveProductUpdatedEvent(String message) {
        try {
            ProductUpdatedEvent event = objectMapper.readValue(message, ProductUpdatedEvent.class);
            System.out.println("Evento recibido en emailMicroservice: " + event);

            sendEmail(event);

        } catch (Exception e) {
            System.err.println("Error al procesar el evento: " + e.getMessage());
        }
    }

    private void sendEmail(ProductUpdatedEvent event) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailName);
            helper.setSubject("Producto actualizado: " + event.getName());

            String emailBody = "<h2>¡El stock del producto ha sido actualizado!</h2>"
                    + "<p><strong>Nombre:</strong> " + event.getName() + "</p>"
                    + "<p><strong>Stock:</strong> " + event.getStock() + "</p>"
                    + "<p><strong>Precio:</strong> " + event.getPrice() + "</p>"
                    + "<p>Gracias por estar con nosotros.</p>";

            helper.setText(emailBody, true);

            mailSender.send(message);
            System.out.println("Correo enviado con éxito");

        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}