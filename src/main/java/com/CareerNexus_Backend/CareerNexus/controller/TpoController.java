package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.dto.StudentDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.dto.TpoDetailsDTO;

import com.CareerNexus_Backend.CareerNexus.service.TpoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("api/tpo")
public class TpoController {

    @Autowired
    private TpoService tpoService;

    @PostMapping("/profile")
    public TpoDetailsDTO Profile(@RequestBody TpoDetailsDTO TpoDetails, Authentication authentication) throws Exception {
        return tpoService.createOrUpdateProfile(authentication.getName(), TpoDetails);
    }

    @GetMapping("/profile")
    public TpoDetailsDTO Profile(Authentication authentication) throws Exception {
        return tpoService.getProfileData(authentication.getName());
    }

    @GetMapping("student/get-profile-links/{year}/{department}")
    public List<StudentDetailsDTO> getProfileLinks(@PathVariable String year, @PathVariable String department){
        return tpoService.getProfileLinks(year,department);
    }
}