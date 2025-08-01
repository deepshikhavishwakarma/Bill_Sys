package com.hcl.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.demo.entity.Invoice;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
