package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.dto.JobPostDTO;
import com.CareerNexus_Backend.CareerNexus.dto.RecruiterJobsDTO;
import com.CareerNexus_Backend.CareerNexus.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;

    @PostMapping("/post")
    public ResponseEntity<JobPostDTO> postJob(@RequestBody JobPostDTO jobPostDTO, Authentication authentication) {
        try {
            String userId = authentication.getName();
            jobPostDTO.setPostedAt(LocalDate.now());
            JobPostDTO createdJob = jobPostService.createJobPost(jobPostDTO,userId);
            return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/recruiter")
    public ResponseEntity<List<JobPostDTO>> getJobsByRecruiter(
            Authentication authentication
    ) {
        try {
            String userId = authentication.getName();
            List<JobPostDTO> jobs = jobPostService.getAllJobsByRecruiter(userId);
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<JobPostDTO>> getAllJobs(Authentication authentication) {
        String userId = authentication.getName();
        List<JobPostDTO> jobs = jobPostService.getAllJobs(userId);
        return ResponseEntity.ok(jobs);
    }


    @PutMapping("/{jobId}")
    public ResponseEntity<JobPostDTO> updateJob(
            @PathVariable Long jobId,
            @RequestBody JobPostDTO updatedJobPostDTO,
            Authentication authentication
    ) {
        try {
            String userId = authentication.getName();
            JobPostDTO updatedJob = jobPostService.updateJobPost(jobId, updatedJobPostDTO, userId);
            return ResponseEntity.ok(updatedJob);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(
            @PathVariable Long jobId,
            Authentication authentication
    ) {
        try {
            String userId = authentication.getName();
            jobPostService.deleteJobPost(jobId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recruiters/job")
    public List<RecruiterJobsDTO> getAll(){
        return  jobPostService.getAll();
    }


}
