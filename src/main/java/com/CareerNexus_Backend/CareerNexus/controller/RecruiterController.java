package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.dto.RecruiterDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/recruiter")
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    @PostMapping("/{userId}/profile")
    public RecruiterDetailsDTO Profile( @RequestPart("imageFile") MultipartFile img,@RequestPart("data") RecruiterDetailsDTO RecruiterDetails, @PathVariable String userId) throws Exception {
        return recruiterService.createOrUpdateProfile(userId,RecruiterDetails,img);

    }

    @GetMapping("/{userId}/profile")
    public RecruiterDetailsDTO Profile(@PathVariable String userId) throws Exception {
        return recruiterService.getProfileData(userId);
    }
}
