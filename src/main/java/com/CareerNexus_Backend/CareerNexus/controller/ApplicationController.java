package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.dto.ApplicationDTO;
import com.CareerNexus_Backend.CareerNexus.dto.JobApplicationCountDTO;
import com.CareerNexus_Backend.CareerNexus.dto.StudentsApplicationsDTO;
import com.CareerNexus_Backend.CareerNexus.exceptions.ResourceNotFoundException;
import com.CareerNexus_Backend.CareerNexus.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping(path = "/apply/{userId}/{jobId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApplicationDTO> applyForJob(@PathVariable Long jobId, @PathVariable String userId, @RequestPart("resumeFile") MultipartFile resumeFile) throws Exception {
        ApplicationDTO createdApplication = applicationService.applyForJob(jobId, userId,resumeFile);
        return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
    }




    @GetMapping("/my-applications/{userId}")
    public ResponseEntity<List<ApplicationDTO>> getMyApplications(@PathVariable String userId) {
        try {
            List<ApplicationDTO> applications = applicationService.getApplicationsByStudent(userId);
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsForJob(@PathVariable Long jobId) {
        try {
            List<ApplicationDTO> applications = applicationService.getApplicationsForJob(jobId);
            return ResponseEntity.ok(applications);
        }  catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/counts/per-job/by-recruiter/{recruiterId}")
    public ResponseEntity<List<JobApplicationCountDTO>> getApplicationCountsPerJobForRecruiter(@PathVariable String recruiterId) {
        try {
            List<JobApplicationCountDTO> counts = applicationService.getApplicationCountsPerJobForRecruiter(recruiterId);
            return ResponseEntity.ok(counts);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  @GetMapping("/student/applications/{id}")
    public List<StudentsApplicationsDTO> getStudentApplicationsForJobId(@PathVariable  Long id){
        return applicationService.getStudentApplicationsForJobId(id);
    }

//    @GetMapping("student/apply/count/{userId}")
//    public  int studentApplicationCount(@PathVariable String userId){
//        return applicationService.getCountApplicationByStudent(userId);
//    }
}