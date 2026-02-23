package com.CareerNexus_Backend.CareerNexus.controller;

// import com.CareerNexus_Backend.CareerNexus.dto.ChangePasswordDTO;
import com.CareerNexus_Backend.CareerNexus.dto.UsersDTO;
import com.CareerNexus_Backend.CareerNexus.exceptions.DuplicateUserException;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.service.EmailService;
import com.CareerNexus_Backend.CareerNexus.service.UserAuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserAuthServiceImplementation userAuthService;

    // ── Student Register ──────────────────────────────────────────────────────

    @PostMapping("/student/register")
    public ResponseEntity<?> studentRegistration(@RequestBody User student) {
        logger.info("Received student registration request for username: {}",
                student.getUserId());
        try {
            student.setRole("student");
            User registeredStudent = userAuthService.registerUser(student);
            logger.info("Student registered successfully: {}",
                    registeredStudent.getUserId());
            return new ResponseEntity<>(registeredStudent, HttpStatus.CREATED);
        } catch (DuplicateUserException e) {
            return new ResponseEntity<>(
                    new DuplicateUserException(HttpStatus.CONFLICT.value(),
                            "User with this userId already exists."),
                    HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new DuplicateUserException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "An unexpected error occurred during registration."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    // this method mainly focus on tpo signup details to store in db
//    @PostMapping("/tpo/register")
//    public ResponseEntity<?> tpoRegistration(@RequestBody User tpo) {
//        logger.info("Received TPO registration request for username: {}", tpo.getUserId());
//        try {
//            tpo.setRole("tpo");
//            User registeredTpo = userAuthService.registerUser(tpo);
//            logger.info("TPO registered successfully: {}", registeredTpo.getUserId());
//            return new ResponseEntity<>(registeredTpo, HttpStatus.CREATED);
//        } catch (DuplicateUserException e) {
//
//            return new ResponseEntity<>(new DuplicateUserException(HttpStatus.CONFLICT.value(), "User with this userId already exists."), HttpStatus.CONFLICT);
//        } catch (Exception e) {
//
//            return new ResponseEntity<>(new DuplicateUserException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred during registration."), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // this method mainly focus on Recruiter signup details to store in db
//    @PostMapping("/recruiter/register")
//    public ResponseEntity<?> recruiterRegistration(@RequestBody User recruiter) {
//        logger.info("Received Recruiter registration request for username: {}", recruiter.getUserId());
//        try {
//            recruiter.setRole("recruiter"); // Fixed typo
//            User registeredRecruiter = userAuthService.registerUser(recruiter);
//            logger.info("Recruiter registered successfully: {}", registeredRecruiter.getUserId());
//            return new ResponseEntity<>(registeredRecruiter, HttpStatus.CREATED);
//        } catch (DuplicateUserException e) {
//
//            return new ResponseEntity<>(new DuplicateUserException(HttpStatus.CONFLICT.value(), "User with this userId already exists."), HttpStatus.CONFLICT);
//        } catch (Exception e) {
//
//            return new ResponseEntity<>(new DuplicateUserException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred during registration."), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    // ── Login ─────────────────────────────────────────────────────────────────

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(
            @RequestBody UsersDTO user) throws AuthenticationException {
        return userAuthService.login(user);
    }

    // ── Change Password ───────────────────────────────────────────────────────
//
//    @PutMapping("/change-password/{userId}")
//    public ResponseEntity<Map<String, String>> changePassword(
//            @PathVariable String userId,
//            @RequestBody ChangePasswordDTO request) {
//        return userAuthService.changePassword(userId, request);
//    }
}