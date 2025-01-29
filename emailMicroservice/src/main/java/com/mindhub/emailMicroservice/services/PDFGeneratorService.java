package com.mindhub.emailMicroservice.services;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mindhub.emailMicroservice.events.OrderCreatedEvent;
import org.springframework.stereotype.Service;

@Service
public class PDFGeneratorService {

    public ByteArrayInputStream generatePdfFromOrder(OrderCreatedEvent orderCreatedEvent) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document();
        try {
                PdfWriter.getInstance(document, out);
                document.open();

                Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD, Color.BLACK);
                Paragraph title = new Paragraph("Confirmación de Orden", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                document.add(new Paragraph("\n"));

                // Información de la orden
                Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
                document.add(new Paragraph("ID de Orden: " + orderCreatedEvent.getId(), boldFont));
                document.add(new Paragraph("Cliente: " + orderCreatedEvent.getUser().getUsername(), boldFont));
                document.add(new Paragraph("Email: " + orderCreatedEvent.getUser().getEmail(), boldFont));
                document.add(new Paragraph("Estado: " + orderCreatedEvent.getStatus(), boldFont));

                document.add(new Paragraph("\n"));

                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);

                Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
                PdfPCell cell;

                cell = new PdfPCell(new Phrase("Cantidad", headerFont));
                cell.setBackgroundColor(Color.DARK_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Producto", headerFont));
                cell.setBackgroundColor(Color.DARK_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Precio Unitario", headerFont));
                cell.setBackgroundColor(Color.DARK_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Subtotal", headerFont));
                cell.setBackgroundColor(Color.DARK_GRAY);
                table.addCell(cell);

                double total = 0;
                for (OrderCreatedEvent.ProductOrderDTO product : orderCreatedEvent.getProducts()) {
                    int quantity = product.getQuantity();
                    String productName = product.getProduct().getName();
                    double price = product.getProduct().getPrice();
                    double subtotal = quantity * price;
                    total += subtotal;

                    table.addCell(String.valueOf(quantity));
                    table.addCell(productName);
                    table.addCell(String.format("$%.2f", price));
                    table.addCell(String.format("$%.2f", subtotal));
                }

                document.add(table);

                Font totalFont = new Font(Font.HELVETICA, 14, Font.BOLD, Color.RED);
                Paragraph totalParagraph = new Paragraph(String.format("Total: $%.2f", total), totalFont);
                totalParagraph.setAlignment(Element.ALIGN_RIGHT);
                document.add(totalParagraph);

                document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
