package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.service.RecruiterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

public class RecruiterController {

    @Autowired
    private RecruiterServices recruiterServices;

    @GetMapping("/check-profile")
    public ResponseEntity<Map<String, String>> checkRecruiterProfile(@RequestParam String userId) {
        boolean isComplete = recruiterServices.isProfileComplete(userId);
        String redirectUrl = isComplete ? "/recruiter/home" : "/recruiter/profile";

        Map<String, String> response = new HashMap<>();
        response.put("redirect", redirectUrl);
        return ResponseEntity.ok(response);
    }
}
