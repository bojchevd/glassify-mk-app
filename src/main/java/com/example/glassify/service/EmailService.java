package com.example.glassify.service;

import com.google.api.client.util.DateTime;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String from, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo("glassifymk@gmail.com");
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public void sendEmailWithAttachment(String attachmentName, byte[] attachment) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("glassifymk@gmail.com");
        helper.setTo("glassifymk@gmail.com");
        helper.setSubject("Order Report");
        helper.setText("Check attached file");

        InputStreamSource attachmentSource = new ByteArrayResource(attachment);
        helper.addAttachment(attachmentName, attachmentSource);

        mailSender.send(mimeMessage);
    }
}
