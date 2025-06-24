package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.model.Student;

import java.util.List;

public interface StudentService {



    List<Student> getAllStudents();
    Student getStudentById(String s_rollNo);
    Student insertStudent(Student student);
    Student updateStudent(Student student);



}
