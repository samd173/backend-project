package com.productweb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

import com.productweb.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email); // 🔥 FIXED

    boolean existsByEmail(String email);

    List<User> findByRole(String role);
}