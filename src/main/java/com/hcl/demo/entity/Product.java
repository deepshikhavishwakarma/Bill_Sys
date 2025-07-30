package com.hcl.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prodId;
	private String prod_Name;
	private String prod_Brand;
	private String category;
	private BigDecimal price;
	private Integer quantity;

	public Product() {
		super();

	}

	public Product(Long prodId, String prod_Name, String prod_Brand, String category, BigDecimal price,
			Integer quantity) {
		super();
		this.prodId = prodId;
		this.prod_Name = prod_Name;
		this.prod_Brand = prod_Brand;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getProd_Name() {
		return prod_Name;
	}

	public void setProd_Name(String prod_Name) {
		this.prod_Name = prod_Name;
	}

	public String getProd_Brand() {
		return prod_Brand;
	}

	public void setProd_Brand(String prod_Brand) {
		this.prod_Brand = prod_Brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
