package com.example.glassify.schedulers;


import com.example.glassify.model.InventoryItem;
import com.example.glassify.model.Order;
//import com.example.glassify.service.InvoiceService;
import com.example.glassify.service.EmailService;
import com.example.glassify.service.InventoryService;
import com.example.glassify.service.InvoiceService;
import com.example.glassify.service.OrderService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
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

    @Autowired
    private InventoryService inventoryService;

    @Scheduled(cron = "0 0 17 * * *")
    public void generateDailyInvoices() throws MessagingException {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);

        List<Order> orderList = orderService.getAllOrdersBetweenDates(yesterday, now);

        byte[] pdfContent = invoiceService.generateInvoice(orderList);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmm");
        String sanitizedDate = now.format(formatter);
        String fileName = "orderReport_" + sanitizedDate + ".pdf";

        String notificationBody = checkInventoryLevels();

        emailService.sendEmailWithAttachment(fileName, pdfContent, notificationBody);

        String filePath = "reports" + File.separator + fileName;
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfContent);
            System.out.println("File saved successfully: " + filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String checkInventoryLevels() {
        StringBuilder notification = new StringBuilder("ПРЕДУПРЕДУВАЊЕ ЗА НИСКА КОЛИЧИНА!\n");
        boolean low = false;
        List<InventoryItem> inventoryItems = inventoryService.getAllInventoryItems();

        for (InventoryItem item : inventoryItems) {
            switch (item.getName()) {
                case "Стакло":
                    if (item.getStockQuantity() < 20) {
                        notification.append("Количината на Стакло е ниска: ").append(item.getStockQuantity()).append("\n");
                        low = true;
                    }
                    break;
                case "Постолје":
                    if (item.getStockQuantity() < 30) {
                        notification.append("Количината на Постолје е ниска: ").append(item.getStockQuantity()).append("\n");
                        low = true;
                    }
                    break;
                case "Рамка А4":
                    if (item.getStockQuantity() < 15) {
                        notification.append("Количината на Рамка А4 е ниска: ").append(item.getStockQuantity()).append("\n");
                        low = true;
                    }
                    break;
                case "Ќесе за достава":
                    if (item.getStockQuantity() < 50) {
                        notification.append("Количината на Ќесе за достава е ниска: ").append(item.getStockQuantity()).append("\n");
                        low = true;
                    }
                    break;
                default:
                    break;
            }
        }

        return low ? notification.toString() : "";
    }
}
