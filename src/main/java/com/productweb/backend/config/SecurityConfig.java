package com.productweb.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {
                })
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/products/**").permitAll() // ✅ allow products
                        .requestMatchers("/auth/**").permitAll() // ✅ allow login
                        .requestMatchers("/").permitAll() // ✅ root allow
                        .anyRequest().permitAll() // 🔥 TEMP FIX (important)
                );

        return http.build();
    }
}