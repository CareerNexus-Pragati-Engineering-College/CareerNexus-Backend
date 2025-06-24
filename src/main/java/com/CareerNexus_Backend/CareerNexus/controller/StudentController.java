package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {

        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{rollNo}")
    public Student getStudent(@PathVariable String rollNo) {
        return studentService.getStudentById(rollNo);
    }

    @PostMapping("/register")
    public ResponseEntity<?> insertStudent(@RequestBody Student student) {
        try {
            Student registeredStudent = studentService.insertStudent(student);
            // If successful, return 201 Created status and the created student object

            return new ResponseEntity<>(registeredStudent, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // For example, if email already exists, throw a custom exception from service
            // and catch it here to return HttpStatus.CONFLICT (409)
            return new ResponseEntity<>("Error registering student: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // HTTP 500
        }
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }
}
