package com.CareerNexus_Backend.CareerNexus.repository;

import com.CareerNexus_Backend.CareerNexus.dto.RecruiterDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.model.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecruiterRepository extends JpaRepository<Recruiter, String> {
    Optional<Recruiter> findByUserId(String UserId);
    @Query("" +
            "select NEW com.CareerNexus_Backend.CareerNexus.dto.RecruiterDetailsDTO(rec.company,rec.img_loc,rec.userId)" +
            "from Recruiter as rec")
    List<RecruiterDetailsDTO> findAllCompanies();

}
