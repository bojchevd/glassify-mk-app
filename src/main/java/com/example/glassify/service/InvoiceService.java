package com.example.glassify.service;

import com.example.glassify.model.Order;
import com.example.glassify.model.OrderItem;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class InvoiceService {

    public byte[] generateInvoice(List<Order> orders) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        try {
            // Load a font that supports Cyrillic (e.g., Times-Roman or any other you prefer)
            PdfFont font = PdfFontFactory.createFont(
                    "src/main/resources/fonts/DejaVuSans.ttf",
                    PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
            document.setFont(font);

            // Add Branding or Header
            document.add(new Paragraph("Glassify Orders")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER.CENTER)
            );

            // Add date and time of generation
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            document.add(new Paragraph("Generated on: " + now.format(formatter))
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT)
            );

            // Line break
            document.add(new Paragraph("\n"));

            // Loop through orders and add order details
            for (Order order : orders) {
                document.add(new Paragraph("Invoice for Order ID: " + order.getId())
                        .setFontSize(14)
                        .setBold()
                        .setTextAlignment(TextAlignment.LEFT)
                );
                document.add(new Paragraph("Full Name: " + order.getFullName()));
                document.add(new Paragraph("Phone Number: " + order.getPhoneNumber()));
                document.add(new Paragraph("City: " + order.getCity()));
                document.add(new Paragraph("Address: " + order.getAddress()));
                document.add(new Paragraph("Email: " + order.getEmail()));
                document.add(new Paragraph("Total Price: " + order.getTotalPrice() + "ден."));
                document.add(new Paragraph("Order Status: " + order.getOrderStatus()));
                document.add(new Paragraph("Created At: " + order.getCreatedAt().toString().substring(0,20)));
                document.add(new Paragraph("\n"));

                // Add a table for the order items
                Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 2, 2, 1, 1, 1}));
                table.addHeaderCell(new Cell().add(new Paragraph("Product")));
                table.addHeaderCell(new Cell().add(new Paragraph("Quantity")));
                table.addHeaderCell(new Cell().add(new Paragraph("Custom Details")));
                table.addHeaderCell(new Cell().add(new Paragraph("Photo URL")));
                table.addHeaderCell(new Cell().add(new Paragraph("Frame Color")));
                table.addHeaderCell(new Cell().add(new Paragraph("Song Name")));
                table.addHeaderCell(new Cell().add(new Paragraph("Artist Name")));

                for (OrderItem item : order.getItems()) {
                    table.addCell(new Cell().add(new Paragraph(item.getProduct().getName())));
                    table.addCell(new Cell().add(new Paragraph(item.getQuantity().toString())));
                    table.addCell(new Cell().add(new Paragraph(item.getCustomDetails() != null ? item.getCustomDetails() : "N/A")));
                    table.addCell(new Cell().add(new Paragraph(item.getPhotoUrl() != null ? item.getPhotoUrl() : "N/A")));
                    table.addCell(new Cell().add(new Paragraph(item.getFrameColor() != null ? item.getFrameColor() : "N/A")));
                    table.addCell(new Cell().add(new Paragraph(item.getSongName() != null ? item.getSongName() : "N/A")));
                    table.addCell(new Cell().add(new Paragraph(item.getArtistName() != null ? item.getArtistName() : "N/A")));
                }

                document.add(table);
                document.add(new Paragraph("\n----------------------------------------\n"));
            }

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }
}

