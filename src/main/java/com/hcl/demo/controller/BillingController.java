package com.hcl.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.demo.entity.Invoice;
import com.hcl.demo.entity.InvoiceItem;
import com.hcl.demo.entity.Product;
import com.hcl.demo.repository.InvoiceRepository;
import com.hcl.demo.repository.ProductRepository;
import com.hcl.demo.service.InvoicePdfService;


@RestController
public class BillingController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InvoicePdfService invoicePdfService;

    @PostMapping("/invoice/create")
    public ResponseEntity<byte[]> submitRecord(@RequestParam String customerName,
                                               @RequestParam String paymentMode,
                                               @RequestParam BigDecimal discount,
                                               @RequestParam List<Long> productsIds,
                                               @RequestParam List<Integer> quantities) throws IOException {

        Invoice invoice = new Invoice();
        invoice.setCustomerName(customerName);
        invoice.setPaymentMode(paymentMode);
        invoice.setDiscount(discount);
        invoice.setDate(LocalDateTime.now());

        List<InvoiceItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (int i = 0; i < productsIds.size(); i++) {
            //Product product = productRepository.findById(productsIds.get(i)).orElseThrow();
            

Long productId = productsIds.get(i);
    Integer quantity = quantities.get(i);

    if (productId == null) continue;

        	
        	Product product = productRepository.findById(productId)
            	    .orElseThrow(() -> new RuntimeException("Product ID  not found"));

            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setProduct(product);
            invoiceItem.setItemQuantity(quantities.get(i));
            invoiceItem.setItemPrice(product.getPrice());
            invoiceItem.setInvoice(invoice);
            items.add(invoiceItem);
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(quantities.get(i))));
        }

        invoice.setItems(items);
        invoice.setTotalAmount(totalAmount.subtract(discount));
        invoiceRepository.save(invoice);

        byte[] pdfBytes = invoicePdfService.generateInvoicePdf(invoice, items);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
