package com.CareerNexus_Backend.CareerNexus.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeUser {

    @GetMapping("/welcome")
    public String welcome(){
        return "hello";
    }
}
