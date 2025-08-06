package com.hcl.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.demo.entity.Invoice;
import com.hcl.demo.service.InvoicePdfService;
import com.hcl.demo.service.InvoiceService;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoicePdfService invoicePdfService;

    @PostMapping("/create")
    public ResponseEntity<String> createInvoice(
            @RequestParam String customerName,
            @RequestParam String paymentMode,
            @RequestParam BigDecimal discount, // Use BigDecimal
            @RequestParam List<Long> productsIds,
            @RequestParam List<Integer> quantities) {
        try {
            Invoice invoice = invoiceService.createInvoice(customerName, paymentMode, discount, productsIds, quantities);
            return ResponseEntity.ok("Invoice created successfully with ID: " + invoice.getInvoiceId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long id) throws IOException {
        Invoice invoice = invoiceService.getInvoiceById(id);
        byte[] pdfBytes = invoicePdfService.generateInvoicePdf(invoice, invoice.getItems());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}