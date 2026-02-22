package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.dto.RecruiterDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.dto.StudentDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.service.RecruiterService;
import com.CareerNexus_Backend.CareerNexus.service.StudentServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {


    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentServices studentServices;

    @Autowired
    private RecruiterService recruiterService;

    @PostMapping("/{userId}/profile")
    public ResponseEntity<StudentDetailsDTO> Profile(@RequestPart("data") StudentDetailsDTO studentDetailsDTO, @RequestPart("imageFile") MultipartFile img, @PathVariable String userId, Authentication authentication) {
        try {

            String authenicatedUserName=authentication.getName();

            if(!authenicatedUserName.equals(userId)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            StudentDetailsDTO createdOrUpdatedProfile = studentServices.createOrUpdateProfile(studentDetailsDTO, userId, img);
            return new ResponseEntity<>(createdOrUpdatedProfile, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating/updating student profile for user: {}", userId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping("/{userId}/profile")
        public ResponseEntity<StudentDetailsDTO> getStudentProfile (@PathVariable String userId,Authentication authentication)
        {
            String authenicatedUserName=authentication.getName();

            if(!authenicatedUserName.equals(userId)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            try {
                StudentDetailsDTO profileData = studentServices.getProfileData(userId);
                return ResponseEntity.ok(profileData);
            }  catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/companies-visited")
        public List<RecruiterDetailsDTO> recentCompanies() {
            return recruiterService.getAllCompanies();
        }
}

