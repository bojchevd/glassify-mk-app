package com.example.glassify.service;

import com.example.glassify.model.Order;
import com.example.glassify.model.OrderItem;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void orderNotification(String from, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo("glassifymk@gmail.com");
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public void orderConfirmation(String from, String to, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Order Confirmation");
            helper.setText(message, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    public String generateOrderConfirmationMessage(Order order) {
        StringBuilder message = new StringBuilder();

        message.append("<html>")
                .append("<body style='font-family: Arial, sans-serif;'>")
                .append("<h2 style='color: #4CAF50;'>УСПЕШНА НАРАЧКА</h2>")
                .append("<p>Tи благодариме за нарачката, ").append(order.getFullName()).append("!</p>")
                .append("<p>Детали за нарачката:</p>")
                .append("<table style='width: 100%; border-collapse: collapse;'>")
                .append("<thead>")
                .append("<tr>")
                .append("<th style='border: 1px solid #ddd; padding: 8px;'>Product</th>")
                .append("<th style='border: 1px solid #ddd; padding: 8px;'>Quantity</th>")
                .append("<th style='border: 1px solid #ddd; padding: 8px;'>Price</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");

        int totalPrice = 0;
        for (OrderItem orderItem : order.getItems()) {
            totalPrice += orderItem.getSubtotal(); // Assuming `price` includes the subtotal calculation

            message.append("<tr>")
                    .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(orderItem.getProduct().getName()).append("</td>")
                    .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(orderItem.getQuantity()).append("</td>")
                    .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(orderItem.getSubtotal()).append("</td>")
                    .append("</tr>");
        }

        message.append("</tbody>")
                .append("</table>")
                .append("<p style='font-weight: bold;'>Вкупна цена: ").append(totalPrice).append("ден.</p>")
                .append("<h3>Податоци за достава</h3>")
                .append("<p>")
                .append("Име и презиме: ").append(order.getFullName()).append("<br>")
                .append("Телефонски број: ").append(order.getPhoneNumber()).append("<br>")
                .append("Адреса: ").append(order.getAddress()).append("<br>")
                .append("Место: ").append(order.getCity()).append("<br>")
                .append("Email: ").append(order.getEmail())
                .append("</p>")
                .append("<p>Ти благодариме за довербата!<br></p>")
                .append("<p>Со почит,<br></p>")
                .append("<p>Glassify.mk</p>")
                .append("</body>")
                .append("</html>");

        return message.toString();
    }


    public void sendEmailWithAttachment(String attachmentName, byte[] attachment, String notificationBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("glassifymk@gmail.com");
        helper.setTo("glassifymk@gmail.com");
        helper.setSubject("Дневен извештај");
        helper.setText(notificationBody + "Извештајот за нарачки е во attachment");

        InputStreamSource attachmentSource = new ByteArrayResource(attachment);
        helper.addAttachment(attachmentName, attachmentSource);

        mailSender.send(mimeMessage);
    }
}
