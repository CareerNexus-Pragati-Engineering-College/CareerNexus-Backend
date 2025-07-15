package com.CareerNexus_Backend.CareerNexus.repository;


import com.CareerNexus_Backend.CareerNexus.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByJobPost_IdAndStudent_UserId(Long id, String studentId);

    List<Application> findByStudent_UserId(String studentUserId);

    List<Application> findByJobPost_Id(Long jobId);

    //List<Application> findByRecruiter_UserId(String recruiterId);
}

