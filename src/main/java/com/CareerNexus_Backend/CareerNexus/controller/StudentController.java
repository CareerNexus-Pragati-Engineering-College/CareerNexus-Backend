package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.dto.RecruiterDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.dto.StudentDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.service.RecruiterService;
import com.CareerNexus_Backend.CareerNexus.service.StudentServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {


    private static final Logger log = LogManager.getLogger(StudentController.class);
    @Autowired
    private StudentServices studentServices;

    @Autowired
    private RecruiterService recruiterService;

    @PostMapping("/{userId}/profile")
        public ResponseEntity<StudentDetailsDTO> Profile(@RequestBody StudentDetailsDTO studentDetailsDTO, @PathVariable String userId,Authentication authentication) {
        try {

            String authenicatedUserName=authentication.getName();

            if(!authenicatedUserName.equals(userId)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            StudentDetailsDTO createdOrUpdatedProfile = studentServices.createOrUpdateProfile(studentDetailsDTO,userId);
            return new ResponseEntity<>(createdOrUpdatedProfile, HttpStatus.CREATED);
        } catch (Exception e) {
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

