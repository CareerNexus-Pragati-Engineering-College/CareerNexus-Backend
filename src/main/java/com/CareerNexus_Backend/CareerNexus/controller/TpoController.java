package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.dto.TpoDetailsDTO;

import com.CareerNexus_Backend.CareerNexus.service.TpoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tpo")
public class TpoController {

    @Autowired
    private TpoService tpoService;

    @PostMapping("/{userId}/profile")
    public TpoDetailsDTO Profile(@RequestBody TpoDetailsDTO TpoDetails, @PathVariable String userId) throws Exception {

        return tpoService.createOrUpdateProfile(userId, TpoDetails);

    }

    @GetMapping("/{userId}/profile")
    public TpoDetailsDTO Profile(@PathVariable String userId) throws Exception {
        return tpoService.getProfileData(userId);
    }
}