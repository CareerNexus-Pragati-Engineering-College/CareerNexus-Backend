package com.CareerNexus_Backend.CareerNexus.repository;



import com.CareerNexus_Backend.CareerNexus.model.JobPost;
import com.CareerNexus_Backend.CareerNexus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    List<JobPost> findByPostedBy(User user); // Accepts a User object

    @Query("SELECT jp FROM JobPost jp " +
            "LEFT JOIN Application app ON app.jobPost = jp AND app.student = :student " +
            "WHERE app.id IS NULL")
    List<JobPost> findNotAppliedJobsByStudent(@Param("student") User student);

}
