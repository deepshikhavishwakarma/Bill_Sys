package com.hcl.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hcl.demo.entity.Invoice;
import com.hcl.demo.entity.InvoiceItem;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

@Service
public class InvoicePdfService {
    public byte[] generateInvoicePdf(Invoice invoice, List<InvoiceItem> invoiceItem) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        document.add(new Paragraph("Retail shop Invoice").setBold().setFontSize(18));
        document.add(new Paragraph("Customer: " + invoice.getCustomerName()));
        document.add(new Paragraph("Date :" + invoice.getDate().toString()));
        document.add(new Paragraph("Payment Mode :" + invoice.getPaymentMode()));
        document.add(new Paragraph("Discount ₹" + invoice.getDiscount()));

        Table table = new Table(new float[]{4, 2, 2, 2});
        table.addHeaderCell("Product");
        table.addHeaderCell("Quantity");
        table.addHeaderCell("Price");
        table.addHeaderCell("Total");

        for (InvoiceItem item : invoiceItem) {
            table.addCell(item.getProduct().getProd_Name());
            table.addCell(String.valueOf(item.getItemQuantity()));
            table.addCell("Rs." + item.getItemPrice());
            
            BigDecimal itemTotal = item.getItemPrice().multiply(BigDecimal.valueOf(item.getItemQuantity()));
            table.addCell("Rs." + itemTotal);
        }

        document.add(table);
        document.add(new Paragraph("Total Amount :Rs." + invoice.getTotalAmount()).setBold());
        document.close();
        return baos.toByteArray();
    }
}