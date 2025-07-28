package com.CareerNexus_Backend.CareerNexus.repository;

import com.CareerNexus_Backend.CareerNexus.model.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecruiterRepository extends JpaRepository<Recruiter, String> {
    Optional<Recruiter> findByUserId(String UserId);


}
