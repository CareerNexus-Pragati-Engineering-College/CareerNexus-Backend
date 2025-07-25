package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.dto.RecruiterDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.model.Recruiter;
import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/recruiter")
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    @PostMapping("/{userId}/profile")
    public RecruiterDetailsDTO Profile(@RequestBody RecruiterDetailsDTO RecruiterDetails, @PathVariable String userId) throws Exception {

        return recruiterService.createOrUpdateProfile(userId,RecruiterDetails);

    }

    @GetMapping("/{userId}/profile")
    public RecruiterDetailsDTO Profile(@PathVariable String userId) throws Exception {
        return recruiterService.getProfileData(userId);
    }
}
