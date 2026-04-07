package com.productweb.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import com.productweb.backend.model.Product;
import com.productweb.backend.repository.ProductRepository;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*") // React connect ke liye
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    // =========================
    // ✅ GET ALL PRODUCTS
    // =========================
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = repo.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching products ❌");
        }
    }

    // =========================
    // ✅ GET PRODUCT BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            Optional<Product> product = repo.findById(id);

            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                return ResponseEntity.status(404).body("Product not found ❌");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching product ❌");
        }
    }

    // =========================
    // ✅ ADD PRODUCT
    // =========================
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product saved = repo.save(product);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving product ❌");
        }
    }

    // =========================
    // ✅ UPDATE PRODUCT
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        try {
            Optional<Product> existing = repo.findById(id);

            if (existing.isPresent()) {

                Product p = existing.get();

                p.setName(product.getName());
                p.setPrice(product.getPrice());
                p.setUnit(product.getUnit());
                p.setQuantity(product.getQuantity());
                p.setCategory(product.getCategory());
                p.setImage(product.getImage());

                Product updated = repo.save(p);

                return ResponseEntity.ok(updated);

            } else {
                return ResponseEntity.status(404).body("Product not found ❌");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating product ❌");
        }
    }

    // =========================
    // ✅ DELETE PRODUCT
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            if (!repo.existsById(id)) {
                return ResponseEntity.status(404).body("Product not found ❌");
            }

            repo.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully ✅");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting product ❌");
        }
    }
}