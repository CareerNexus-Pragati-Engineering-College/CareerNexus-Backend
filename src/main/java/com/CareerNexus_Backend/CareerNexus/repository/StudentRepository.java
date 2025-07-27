package com.CareerNexus_Backend.CareerNexus.repository;

import com.CareerNexus_Backend.CareerNexus.dto.StudentDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findByUserId(String UserId);


    @Query("SELECT NEW com.CareerNexus_Backend.CareerNexus.dto.StudentDetailsDTO(s.userId, s.FirstName, s.LastName, s.urls) " + // Assuming resumeUrl is the field
            "FROM Student s " +
            "WHERE s.Year = :year AND s.Department = :department ") // <--- ALL SELECTED FIELDS HERE
    List<StudentDetailsDTO> findStudentsByYearAndDepartment(
            @Param("year") String year,
            @Param("department") String department
    );
}