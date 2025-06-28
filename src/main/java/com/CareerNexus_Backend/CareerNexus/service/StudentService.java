package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.model.Student;

public interface StudentService {
    boolean hasProfile(String userId);
    Student getStudentProfile(String userId);
    Student createOrUpdateProfile(Student student);
}