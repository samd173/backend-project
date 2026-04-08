package com.productweb.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

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

    // =========================
    // 🔥 1. SAVE ORDER
    // =========================
    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody Order order) {

        try {
            // ✅ Default status
            if (order.getStatus() == null) {
                order.setStatus("Pending");
            }

            // ✅ ETA
            order.setEta("30 mins");

            // 🔥 USER ATTACH
            if (order.getUser() != null && order.getUser().getId() != null) {

                Optional<User> optionalUser = userRepo.findById(order.getUser().getId());

                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();

                    order.setUser(user);
                    order.setCustomer(user.getName());

                } else {
                    return ResponseEntity.status(404).body("User not found ❌");
                }

            } else {
                return ResponseEntity.badRequest().body("User data missing ❌");
            }

            // 🔥 PAYMENT DEFAULT
            if (order.getPaymentMethod() == null) {
                order.setPaymentMethod("COD");
            }

            Order savedOrder = repo.save(order);

            return ResponseEntity.ok(savedOrder);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving order ❌");
        }
    }

    // =========================
    // 🔥 2. GET ALL ORDERS
    // =========================
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = repo.findAll();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching orders ❌");
        }
    }

    // =========================
    // 🔥 3. GET USER ORDERS
    // =========================
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserOrders(@PathVariable Long id) {
        try {
            List<Order> orders = repo.findByUserId(id);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching user orders ❌");
        }
    }

    // =========================
    // 🔥 4. UPDATE STATUS
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Order updated) {

        try {
            Optional<Order> optionalOrder = repo.findById(id);

            if (optionalOrder.isPresent()) {

                Order order = optionalOrder.get();

                order.setStatus(updated.getStatus());
                order.setEta(updated.getEta());

                Order saved = repo.save(order);

                return ResponseEntity.ok(saved);

            } else {
                return ResponseEntity.status(404).body("Order not found ❌");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating order ❌");
        }
    }

    // =========================
    // 🔥 5. DELETE ORDER (OPTIONAL)
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            if (!repo.existsById(id)) {
                return ResponseEntity.status(404).body("Order not found ❌");
            }

            repo.deleteById(id);
            return ResponseEntity.ok("Order deleted successfully ✅");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting order ❌");
        }
    }
}