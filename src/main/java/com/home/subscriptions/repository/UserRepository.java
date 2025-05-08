package com.home.subscriptions.repository;

import com.home.subscriptions.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CustomUser, Long> {
    boolean existsByEmail(String email);
}
