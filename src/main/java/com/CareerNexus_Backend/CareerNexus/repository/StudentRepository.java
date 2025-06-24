package com.CareerNexus_Backend.CareerNexus.repository;
import com.CareerNexus_Backend.CareerNexus.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,String> {
}
