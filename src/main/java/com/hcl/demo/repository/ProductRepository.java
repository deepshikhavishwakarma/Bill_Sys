package com.hcl.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.demo.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
