package com.CareerNexus_Backend.CareerNexus.repository;


import com.CareerNexus_Backend.CareerNexus.dto.JobApplicationCountDTO;
import com.CareerNexus_Backend.CareerNexus.model.Application;
import com.CareerNexus_Backend.CareerNexus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;


public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByJobPost_IdAndStudent_UserId(Long id, String studentId);

    List<Application> findByStudent_UserId(String studentUserId);

    List<Application> findByJobPost_Id(Long jobId);


    @Query("SELECT NEW com.CareerNexus_Backend.CareerNexus.dto.JobApplicationCountDTO(jp.id, jp.jobTitle, jp.companyName, COUNT(a)) " +
            "FROM Application a JOIN a.jobPost jp " +
            "WHERE jp.postedBy = :recruiter " +
            "GROUP BY jp.id, jp.jobTitle, jp.companyName")
    List<JobApplicationCountDTO> countApplicationsWithDetailsPerJobForRecruiter(@Param("recruiter") User recruiter); // <--- **CHANGED: Parameter type is User**




}

