package com.productweb.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    // 🔥 TEST API (CHECK SERVER RUNNING)
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Backend running 🚀 (DB disabled)");
    }

    // 🔥 FAKE USER LIST (NO DB)
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {

        List<Map<String, Object>> users = new ArrayList<>();

        Map<String, Object> u1 = new HashMap<>();
        u1.put("id", 1);
        u1.put("name", "Samadhan");
        u1.put("email", "samadhan@gmail.com");

        users.add(u1);

        return ResponseEntity.ok(users);
    }
}