package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.dto.ChangePasswordDTO;
import com.CareerNexus_Backend.CareerNexus.dto.RecruiterDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.dto.StudentDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.service.RecruiterService;
import com.CareerNexus_Backend.CareerNexus.service.StudentServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")  // ← added leading slash
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentServices studentServices;

    @Autowired
    private RecruiterService recruiterService;

    // ── Create or Update Profile ──────────────────────────────────────────────

    @PostMapping("/profile")
    public ResponseEntity<StudentDetailsDTO> profile(
            @RequestPart("data") StudentDetailsDTO studentDetailsDTO,
            @RequestPart("imageFile") MultipartFile img,
            Authentication authentication) {
        try {
            String userId = authentication.getName();
            StudentDetailsDTO createdOrUpdatedProfile = studentServices
                    .createOrUpdateProfile(studentDetailsDTO, userId, img);
            return new ResponseEntity<>(createdOrUpdatedProfile, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating/updating student profile for user: {}",
                    authentication.getName(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ── Get Profile ───────────────────────────────────────────────────────────

    @GetMapping("/profile")
    public ResponseEntity<StudentDetailsDTO> getStudentProfile(
            Authentication authentication) {
        String userId = authentication.getName();
        try {
            StudentDetailsDTO profileData = studentServices.getProfileData(userId);
            return ResponseEntity.ok(profileData);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ── Companies Visited ─────────────────────────────────────────────────────

    @GetMapping("/companies-visited")
    public List<RecruiterDetailsDTO> recentCompanies() {
        return recruiterService.getAllCompanies();
    }

    // ── Change Password ───────────────────────────────────────────────────────

    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            Authentication authentication,
            // userId taken from JWT token

            @RequestBody ChangePasswordDTO request) {

        String userId = authentication.getName();
        return studentServices.changePassword(userId, request);
    }
}