package com.CareerNexus_Backend.CareerNexus.repository;


import com.CareerNexus_Backend.CareerNexus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
}