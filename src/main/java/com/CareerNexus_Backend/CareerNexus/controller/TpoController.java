package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.dto.StudentDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.dto.TpoDetailsDTO;

import com.CareerNexus_Backend.CareerNexus.service.TpoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("api/tpo")
public class TpoController {

    @Autowired
    private TpoService tpoService;

    private static final Logger logger = LoggerFactory.getLogger(TpoController.class);

    @PostMapping("/profile")
    public TpoDetailsDTO Profile(@RequestBody TpoDetailsDTO TpoDetails, Authentication authentication) throws Exception {
        logger.info("Received request to create/update profile for TPO: {}", authentication.getName());
        return tpoService.createOrUpdateProfile(authentication.getName(), TpoDetails);
    }

    @GetMapping("/profile")
    public TpoDetailsDTO Profile(Authentication authentication) throws Exception {
        logger.info("Received request to fetch profile for TPO: {}", authentication.getName());
        return tpoService.getProfileData(authentication.getName());
    }

    @GetMapping("student/get-profile-links/{year}/{department}")
    public List<StudentDetailsDTO> getProfileLinks(@PathVariable String year, @PathVariable String department){
        return tpoService.getProfileLinks(year,department);
    }
}