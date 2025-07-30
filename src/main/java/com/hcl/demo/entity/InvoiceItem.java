package com.hcl.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class InvoiceItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;
	@ManyToOne
	@JoinColumn(name = "invoiceId")
	private Invoice invoice;
	@ManyToOne
	@JoinColumn(name = "prodId")
	private Product product;
	private Integer itemQuantity;
	private BigDecimal itemPrice;

	public InvoiceItem() {
		super();
	}

	public InvoiceItem(Long itemId, Invoice invoice, Product product, Integer itemQuantity, BigDecimal itemPrice) {
		super();
		this.itemId = itemId;
		this.invoice = invoice;
		this.product = product;
		this.itemQuantity = itemQuantity;
		this.itemPrice = itemPrice;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

}
