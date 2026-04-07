package com.productweb.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import com.productweb.backend.model.User;
import com.productweb.backend.repository.UserRepository;
import com.productweb.backend.config.JwtUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    // 🔐 TOKEN EXTRACT
    private String getToken(String authHeader) {
        return authHeader.replace("Bearer ", "");
    }

    // =========================
    // 🔥 GET USER BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String auth) {

        try {
            JwtUtil.validateToken(getToken(auth));

            Optional<User> user = repo.findById(id);

            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(404).body("User not found ❌");
            }

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized ❌");
        }
    }

    // =========================
    // 🔥 GET USER BY EMAIL
    // =========================
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(
            @PathVariable String email,
            @RequestHeader("Authorization") String auth) {

        try {
            JwtUtil.validateToken(getToken(auth));

            Optional<User> user = repo.findByEmail(email);

            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(404).body("User not found ❌");
            }

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized ❌");
        }
    }

    // =========================
    // 🔥 UPDATE USER
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody User updated,
            @RequestHeader("Authorization") String auth) {

        try {
            JwtUtil.validateToken(getToken(auth));

            Optional<User> optionalUser = repo.findById(id);

            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(404).body("User not found ❌");
            }

            User user = optionalUser.get();

            user.setName(updated.getName());
            user.setPhone(updated.getPhone());
            user.setAddress(updated.getAddress());
            user.setImage(updated.getImage());

            return ResponseEntity.ok(repo.save(user));

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized ❌");
        }
    }

    // =========================
    // 🔥 ADMIN: GET ALL USERS
    // =========================
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(
            @RequestHeader("Authorization") String auth) {

        try {
            String token = getToken(auth);

            JwtUtil.validateToken(token);
            String role = JwtUtil.getRole(token);

            if (!"ADMIN".equals(role)) {
                return ResponseEntity.status(403)
                        .body("Access denied ❌ (Admin only)");
            }

            return ResponseEntity.ok(repo.findAll());

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized ❌");
        }
    }

    // =========================
    // 🔥 ADMIN: DELETE USER
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id,
            @RequestHeader("Authorization") String auth) {

        try {
            String token = getToken(auth);

            JwtUtil.validateToken(token);
            String role = JwtUtil.getRole(token);

            if (!"ADMIN".equals(role)) {
                return ResponseEntity.status(403)
                        .body("Access denied ❌ (Admin only)");
            }

            if (!repo.existsById(id)) {
                return ResponseEntity.status(404).body("User not found ❌");
            }

            repo.deleteById(id);

            return ResponseEntity.ok("User deleted successfully ✅");

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized ❌");
        }
    }
}