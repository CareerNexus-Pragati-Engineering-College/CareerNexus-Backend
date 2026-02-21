package com.CareerNexus_Backend.CareerNexus.repository;


import com.CareerNexus_Backend.CareerNexus.dto.ApplicationDTO;
import com.CareerNexus_Backend.CareerNexus.dto.JobApplicationCountDTO;
import com.CareerNexus_Backend.CareerNexus.dto.StudentsApplicationsDTO;
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


    @Query("SELECT NEW com.CareerNexus_Backend.CareerNexus.dto.JobApplicationCountDTO(jp.id, COUNT(a)) " +
            "FROM Application a JOIN a.jobPost jp " +
            "WHERE jp.id = :jobId "+
            "GROUP BY jp.id"
            )
    JobApplicationCountDTO countApplicationByJobId(@Param("jobId")Long jobId);

    @Query("SELECT NEW com.CareerNexus_Backend.CareerNexus.dto.StudentsApplicationsDTO(" +
            "jp.id, " +
            "s_user.userId,  sd.FirstName, sd.LastName, sd.Email,sd.Department,sd.Year, " +
            "app.id, app.applicationDate, app.status,sd.urls, app.appliedResumeUrl)" +
            "FROM Application app " +
            "JOIN app.jobPost jp " +
            "JOIN app.student s_user " +
            "LEFT JOIN Student sd ON sd.userId = s_user.userId "+
            "WHERE jp.id = :Id"

    )
    List<StudentsApplicationsDTO> findAllStudentsApplications(@Param("Id") Long id);

    @Query("SELECT NEW com.CareerNexus_Backend.CareerNexus.dto.JobApplicationCountDTO(COUNT(a)) " +
            "FROM Application a " +
            "WHERE a.student.userId = :userId")
    JobApplicationCountDTO findCount(@Param("userId") String userId);


    /**
     * Finds all students who have applied for a specific job.
     * Updated to use the 'Application' entity to match the repository's context.
     */
    @Query("SELECT a.student FROM Application a WHERE a.jobPost.id = :jobId")
    List<User> findStudentsByJobId(@Param("jobId") Long jobId);
}

