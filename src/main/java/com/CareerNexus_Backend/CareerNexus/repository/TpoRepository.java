package com.CareerNexus_Backend.CareerNexus.repository;

import com.CareerNexus_Backend.CareerNexus.model.TPO;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;



public interface TpoRepository  extends JpaRepository<TPO, String> {
    Optional<TPO> findByUserId(String UserId);
}
