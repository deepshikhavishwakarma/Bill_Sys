package com.hcl.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long invoiceId;
	private String customerName;
	private String paymentMode;
	private LocalDateTime date;
	private BigDecimal discount;
	private BigDecimal totalAmount;
	@OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL)
	private List<InvoiceItem> items;

	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Invoice(Long invoiceId, String customerName, String paymentMode, LocalDateTime date, BigDecimal discount,
			BigDecimal totalAmount, List<InvoiceItem> items) {
		super();
		this.invoiceId = invoiceId;
		this.customerName = customerName;
		this.paymentMode = paymentMode;
		this.date = date;
		this.discount = discount;
		this.totalAmount = totalAmount;
		this.items = items;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<InvoiceItem> getItems() {
		return items;
	}

	public void setItems(List<InvoiceItem> items) {
		this.items = items;
	}

}
