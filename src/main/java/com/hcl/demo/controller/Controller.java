package com.hcl.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hcl.demo.service.Studentservice;

@org.springframework.stereotype.Controller
public class Controller {
	
	@Autowired
	private Studentservice service;
	@GetMapping("/billing")
	public String home() {
		return "billing";
	}
}