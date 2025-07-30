package com.hcl.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//@RestController
//@RequestMapping("/api/v1")
@Entity
@Table(name = "Students")
public class Student {
	
//	@Operation(summary = "Get Response")
//	@GetMapping("/welcome")
//	public String getResponse() {
//		 
//		return "Hello!!!"; 
//	} 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstname;
	private String Lastname;
	private String email;
	
	
}
