package com.productweb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.productweb.backend.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 🔥 GET ORDERS BY USER ID (BEST PRACTICE)
    List<Order> findByUserId(Long userId);

}