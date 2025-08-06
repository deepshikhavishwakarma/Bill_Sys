package com.hcl.demo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.demo.entity.Invoice;
import com.hcl.demo.entity.InvoiceItem;
import com.hcl.demo.entity.Product;
import com.hcl.demo.repository.InvoiceItemRepository;
import com.hcl.demo.repository.InvoiceRepository;
import com.hcl.demo.repository.ProductRepository;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    public Invoice createInvoice(String customerName, String paymentMode, BigDecimal discount,
                                 List<Long> productIds, List<Integer> quantities) {
        if (productIds == null || productIds.isEmpty() || productIds.size() != quantities.size()) {
            throw new RuntimeException("Invalid product or quantity list provided.");
        }

        Invoice invoice = new Invoice();
        invoice.setCustomerName(customerName);
        invoice.setPaymentMode(paymentMode);
        invoice.setDiscount(discount);
        invoice.setDate(LocalDateTime.now());
        
        // Save the invoice first to get the generated ID
        Invoice savedInvoice = invoiceRepository.save(invoice);

        List<InvoiceItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            Integer quantity = quantities.get(i);
            
            if (quantity <= 0) {
                continue; // Skip items with non-positive quantity
            }
            
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Invalid product ID: " + productId));

            InvoiceItem item = new InvoiceItem();
            item.setInvoice(savedInvoice); // Link item to the new invoice
            item.setProduct(product);
            item.setItemQuantity(quantity);
            item.setItemPrice(product.getPrice());
            
            // Calculate item total and add to grand total
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            totalAmount = totalAmount.add(itemTotal);
            items.add(item);
        }

        if (items.isEmpty()) {
            throw new RuntimeException("No valid products found. Invoice cannot be created.");
        }

        // Save all the items and link them to the invoice
        List<InvoiceItem> savedItems = invoiceItemRepository.saveAll(items);

        // Update the invoice with the final total amount and items list
        invoice.setItems(savedItems);
        invoice.setTotalAmount(totalAmount.subtract(discount));
        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
    }
}