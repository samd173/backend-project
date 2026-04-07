
package com.productweb.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Backend running 🚀";
    }

    @GetMapping("/test")
    public String test() {
        return "API working ✅";
    }
}
