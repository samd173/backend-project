/* package com.productweb.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import com.productweb.backend.model.User;
import com.productweb.backend.repository.UserRepository;
import com.productweb.backend.config.JwtUtil;
/*
 * @RestController
 * 
 * @CrossOrigin(origins = "*")
 * 
 * @RequestMapping("/auth")
 * public class AuthController {
 * 
 * private final UserRepository userRepo;
 * 
 * public AuthController(UserRepository userRepo) {
 * this.userRepo = userRepo;
 * }
 * 
 * // 🔐 LOGIN (WITH ROLE + JWT)
 * 
 * @PostMapping("/login")
 * public ResponseEntity<?> login(@RequestBody User user) {
 * 
 * User dbUser = userRepo.findByEmailAndPassword(
 * user.getEmail(),
 * user.getPassword());
 * 
 * if (dbUser != null) {
 * 
 * // 🔥 TOKEN WITH ROLE
 * String token = JwtUtil.generateToken(
 * dbUser.getEmail(),
 * dbUser.getRole());
 * 
 * Map<String, Object> response = new HashMap<>();
 * response.put("token", token);
 * response.put("role", dbUser.getRole());
 * response.put("name", dbUser.getName());
 * response.put("email", dbUser.getEmail());
 * response.put("id", dbUser.getId());
 * 
 * return ResponseEntity.ok(response);
 * 
 * } else {
 * 
 * Map<String, String> error = new HashMap<>();
 * error.put("error", "Invalid credentials ❌");
 * 
 * return ResponseEntity.status(401).body(error);
 * }
 * }
 * 
 * // 🆕 REGISTER
 * 
 * @PostMapping("/register")
 * public ResponseEntity<?> register(@RequestBody User user) {
 * 
 * if (userRepo.existsByEmail(user.getEmail())) {
 * 
 * Map<String, String> error = new HashMap<>();
 * error.put("error", "Email already exists ❌");
 * 
 * return ResponseEntity.status(400).body(error);
 * }
 * 
 * user.setRole("USER");
 * userRepo.save(user);
 * 
 * Map<String, String> res = new HashMap<>();
 * res.put("message", "User registered successfully ✅");
 * 
 * return ResponseEntity.ok(res);
 * }
 * }
 */

package com.productweb.backend.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    // 🔥 LOGIN (FAKE)
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> req) {

        Map<String, Object> res = new HashMap<>();

        res.put("message", "Login success (dummy)");
        res.put("token", "fake-jwt-token");

        return res;
    }
}