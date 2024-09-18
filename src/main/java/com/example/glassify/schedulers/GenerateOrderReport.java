package com.example.glassify.schedulers;


import com.example.glassify.model.Order;
//import com.example.glassify.service.InvoiceService;
import com.example.glassify.service.EmailService;
import com.example.glassify.service.InvoiceService;
import com.example.glassify.service.OrderService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class GenerateOrderReport {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailService emailService;
    @Scheduled(cron = "0 0 17 * * *")
    public void generateDailyInvoices() throws MessagingException {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(3);

        List<Order> orderList = orderService.getAllOrdersBetweenDates(yesterday, now);

        byte[] pdfContent = invoiceService.generateInvoice(orderList);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmm");
        String sanitizedDate = now.format(formatter);
        String attachmentName = "orderReport_" + sanitizedDate + ".pdf";

        emailService.sendEmailWithAttachment(attachmentName, pdfContent);

        try (FileOutputStream fos = new FileOutputStream(attachmentName)) {
            fos.write(pdfContent);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
