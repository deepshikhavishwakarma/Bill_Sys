package com.hcl.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.demo.entity.InvoiceItem;

public interface InvoiceItemRepository  extends JpaRepository<InvoiceItem, Long>  {

}
