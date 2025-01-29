package com.mindhub.emailMicroservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.emailMicroservice.events.ProductUpdatedEvent;
import com.mindhub.emailMicroservice.events.RegisteredUser;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class UserRegistredConsumer {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailName;

    @RabbitListener(queues = "user.queue")
    public void receiveRegisteredUser(String message) {
        try {
            RegisteredUser event = objectMapper.readValue(message, RegisteredUser.class);
            System.out.println("Evento recibido en emailMicroservice: " + event);

            sendEmail(event);

        } catch (Exception e) {
            System.err.println("Error al procesar el evento: " + e.getMessage());
        }
    }

    private void sendEmail(RegisteredUser event) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailName);
            helper.setSubject("Usuario creado: " + event.getUsername());

            String emailBody = "<h2>¡Gracias por registrarte!</h2>"
                    + "<p><strong>Nombre:</strong> " + event.getUsername() + "</p>"
                    + "<p><strong>Email:</strong> " + event.getEmail() + "</p>"
                    + "<p>Gracias por estar con nosotros.</p>";

            helper.setText(emailBody, true);

            mailSender.send(message);
            System.out.println("Correo enviado con éxito");

        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}
