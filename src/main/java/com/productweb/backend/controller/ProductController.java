package com.productweb.backend.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import com.productweb.backend.model.Product;
import com.productweb.backend.repository.ProductRepository;

@RestController

@RequestMapping("/products")

@CrossOrigin(origins = "*") // 🔥 React connect ke liye
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    // ✅ GET ALL PRODUCTS

    @GetMapping
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    // ✅ GET PRODUCT BY ID

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        Optional<Product> product = repo.findById(id);
        return product.orElse(null);
    }

    // ✅ ADD PRODUCT

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return repo.save(product);
    }

    // ✅ UPDATE PRODUCT

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {

        Optional<Product> existing = repo.findById(id);

        if (existing.isPresent()) {
            Product p = existing.get();

            p.setName(product.getName());
            p.setPrice(product.getPrice());
            p.setUnit(product.getUnit());
            p.setQuantity(product.getQuantity());
            p.setCategory(product.getCategory());
            p.setImage(product.getImage()); // 🔥 IMAGE IMPORTANT

            return repo.save(p);
        } else {
            return null;
        }
    }

    // ✅ DELETE PRODUCT

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        repo.deleteById(id);
        return "Deleted Successfully";
    }
}
