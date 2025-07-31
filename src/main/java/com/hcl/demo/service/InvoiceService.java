package com.hcl.demo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Invoice createInvoice(String customerName, String paymentMode, double discount,
                                  List<Long> productIds, List<Integer> quantities) {

        if (productIds == null || productIds.isEmpty()) {
            throw new RuntimeException("No products selected");
        }

        List<InvoiceItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            int quantity = quantities.get(i);

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Invalid product ID: " + productId));

            InvoiceItem item = new InvoiceItem();
            item.setProduct(product);
            item.setItemQuantity(quantity);

            BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            item.setItemPrice(price);

            totalAmount = totalAmount.add(price);
            items.add(item);
        }

        BigDecimal discountAmount = BigDecimal.valueOf(discount);
        totalAmount = totalAmount.subtract(discountAmount);

        Invoice invoice = new Invoice();
        invoice.setCustomerName(customerName);
        invoice.setPaymentMode(paymentMode);
        invoice.setDiscount(discountAmount);
        invoice.setTotalAmount(totalAmount);
        invoice.setDate(LocalDateTime.now());
        invoice = invoiceRepository.save(invoice);

        for (InvoiceItem item : items) {
            item.setInvoice(invoice);
            invoiceItemRepository.save(item);
        }

        invoice.setItems(items);
        return invoice;
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
    }
}
