package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.model.Recruiter;
import com.CareerNexus_Backend.CareerNexus.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    @PostMapping("/{userId}/profile")
    public Recruiter Profile(@RequestBody Recruiter RecruiterDetails, @PathVariable String userId) {

        return recruiterService.createOrUpdateProfile(RecruiterDetails, userId);

    }
}
