package com.CareerNexus_Backend.CareerNexus.repository;



import com.CareerNexus_Backend.CareerNexus.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findByRecruiterUserId(String recruiterUserId);
}
