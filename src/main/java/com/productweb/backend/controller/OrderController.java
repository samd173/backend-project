package com.productweb.backend.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.productweb.backend.model.Order;
import com.productweb.backend.model.User;
import com.productweb.backend.repository.OrderRepository;
import com.productweb.backend.repository.UserRepository;

@RestController

@CrossOrigin(origins = "*")

@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repo;
    private final UserRepository userRepo;

    public OrderController(OrderRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    // 🔥 1. SAVE ORDER

    @PostMapping
    public Order saveOrder(@RequestBody Order order) {

        System.out.println("📦 Incoming Order: " + order);

        // ✅ FIX: Status overwrite nahi karega
        if (order.getStatus() == null) {
            order.setStatus("Pending");
        }

        // ETA always set
        order.setEta("30 mins");

        try {
            // 🔥 USER ATTACH + CUSTOMER NAME
            if (order.getUser() != null && order.getUser().getId() != null) {

                User user = userRepo.findById(order.getUser().getId()).orElse(null);

                if (user != null) {
                    order.setUser(user);

                    // ✅ Customer name set
                    order.setCustomer(user.getName());

                } else {
                    System.out.println("❌ User not found in DB");
                }

            } else {
                System.out.println("❌ User data missing from request");
            }

            // 🔥 PAYMENT METHOD SAFETY
            if (order.getPaymentMethod() == null) {
                order.setPaymentMethod("COD");
            }

            // 💾 SAVE ORDER
            Order savedOrder = repo.save(order);

            System.out.println("✅ Order Saved: " + savedOrder.getId());

            return savedOrder;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 🔥 2. GET ALL ORDERS (ADMIN)

    @GetMapping
    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    // 🔥 3. GET USER ORDERS

    @GetMapping("/user/{id}")
    public List<Order> getUserOrders(@PathVariable Long id) {
        return repo.findByUserId(id);
    }

    // 🔥 4. UPDATE STATUS

    @PutMapping("/{id}")
    public Order updateStatus(@PathVariable Long id, @RequestBody Order updated) {

        Order order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found ❌"));

        order.setStatus(updated.getStatus());
        order.setEta(updated.getEta());

        return repo.save(order);
    }
}
