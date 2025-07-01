package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.service.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/student")
public class StudentController {

    @Autowired
    private StudentServices studentServices;

    @PostMapping("/{userId}/profile")
        public Student Profile(@RequestBody Student StudentDetails, @PathVariable String userId) {
       return studentServices.createOrUpdateProfile(StudentDetails,userId);
        }

        @GetMapping("/{userId}/profile")
        public Optional<Student> Profile(@PathVariable String userId){
        return studentServices.getProfileData(userId);
        }
}

