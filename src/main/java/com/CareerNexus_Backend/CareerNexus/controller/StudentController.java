package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.dto.StudentDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.service.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/student")
public class StudentController {


    @Autowired
    private StudentServices studentServices;

    @PostMapping("/{userId}/profile")
        public ResponseEntity<StudentDetailsDTO> Profile(@RequestBody StudentDetailsDTO studentDetailsDTO, @PathVariable String userId) {
        try {
            StudentDetailsDTO createdOrUpdatedProfile = studentServices.createOrUpdateProfile(studentDetailsDTO,userId);
            return new ResponseEntity<>(createdOrUpdatedProfile, HttpStatus.CREATED); // Or HttpStatus.OK if it's an update
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }

        @GetMapping("/{userId}/profile")
        public ResponseEntity<StudentDetailsDTO> getStudentProfile (@PathVariable String userId)
        {

            try {
                StudentDetailsDTO profileData = studentServices.getProfileData(userId);
                return ResponseEntity.ok(profileData);
            }  catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}

