package com.productweb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

import com.productweb.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // 🔥 LOGIN (SAFE WAY)
    Optional<User> findByEmail(String email);

    // 🔥 CHECK EMAIL EXIST
    boolean existsByEmail(String email);

    // 🔥 FILTER USERS BY ROLE
    List<User> findByRole(String role);
}