package com.hcl.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.demo.entity.Product;
import com.hcl.demo.repository.ProductRepository;

@RestController
public class ProductController {
	// In your ProductController.java
	 @Autowired // This annotation injects the ProductRepository
	    private ProductRepository productRepository;
	
	@GetMapping("/products")
	public List<Product> getAllProducts() {
	    return productRepository.findAll();
	}
}
