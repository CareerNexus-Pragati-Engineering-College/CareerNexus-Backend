package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.dto.JobPostDTO;

import com.CareerNexus_Backend.CareerNexus.service.JobPostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;

    @PostMapping("/post/{userId}")
    public ResponseEntity<JobPostDTO> postJob(@RequestBody JobPostDTO jobPostDTO, @PathVariable String userId) {
        try {


            jobPostDTO.setPostedAt(LocalDate.now());
            JobPostDTO createdJob = jobPostService.createJobPost(jobPostDTO,userId);
            return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/recruiter/{userId}")
    public ResponseEntity<List<JobPostDTO>> getJobsByRecruiter(
            @PathVariable String userId
    ) {
        try {
            List<JobPostDTO> jobs = jobPostService.getAllJobsByRecruiter(userId);
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<JobPostDTO>> getAllJobs() {
        List<JobPostDTO> jobs = jobPostService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }


    @PutMapping("/{userId}/{jobId}") // PUT maps to resource ID
    public ResponseEntity<JobPostDTO> updateJob(
            @PathVariable String userId,
            @PathVariable Long jobId,
            @RequestBody JobPostDTO updatedJobPostDTO


    ) {
        try {

            JobPostDTO updatedJob = jobPostService.updateJobPost(jobId, updatedJobPostDTO, userId);
            return ResponseEntity.ok(updatedJob);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // If user is not authorized to update this job
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{userId}/{jobId}") // Use consistent variable name
    public ResponseEntity<Void> deleteJob(
            @PathVariable String userId,
            @PathVariable Long jobId

    ) {
        try {

            jobPostService.deleteJobPost(jobId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}