package com.productweb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.productweb.backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}