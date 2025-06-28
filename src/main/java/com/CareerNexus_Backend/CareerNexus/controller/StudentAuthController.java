package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.repository.StudentRepository;
import com.CareerNexus_Backend.CareerNexus.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class StudentAuthController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@RequestBody Student loginRequest) {
        Student student = studentRepository.findByUserIdAndPassword(
                loginRequest.getUserId(), loginRequest.getPassword());

        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        boolean hasProfile = studentService.hasProfile(student.getUserId());
        String nextPage = hasProfile ? "/student/home" : "/student/profile";

        Map<String, Object> response = new HashMap<>();
        response.put("studentId", student.getUserId());
        response.put("next", nextPage);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getStudentProfile(@PathVariable String userId) {
        Student student = studentService.getStudentProfile(userId);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> updateStudentProfile(@RequestBody Student student) {
        try {
            Student updatedStudent = studentService.createOrUpdateProfile(student);
            return ResponseEntity.ok(updatedStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating profile: " + e.getMessage());
        }
    }

    @GetMapping("/check-profile/{userId}")
    public ResponseEntity<?> checkProfileExists(@PathVariable String userId) {
        boolean hasProfile = studentService.hasProfile(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("hasProfile", hasProfile);
        return ResponseEntity.ok(response);
    }
}