package com.mindhub.emailMicroservice.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailWithAttachment(String toEmail, ByteArrayInputStream pdfContent, String userName) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject(userName + ", tu orden ha sido creada");
            helper.setText("Puedes echar un vistazo a tu orden en el archivo adjunto.");

            byte[] pdfBytes = toByteArray(pdfContent);

            ByteArrayResource resource = new ByteArrayResource(pdfBytes);

            helper.addAttachment("order_confirmation.pdf", resource);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] toByteArray(ByteArrayInputStream byteArrayInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int nRead;
        byte[] buffer = new byte[1024];
        while ((nRead = byteArrayInputStream.read(buffer, 0, buffer.length)) != -1) {
            byteArrayOutputStream.write(buffer, 0, nRead);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
