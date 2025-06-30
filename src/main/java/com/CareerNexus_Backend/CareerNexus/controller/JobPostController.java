package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.model.JobPost;
import com.CareerNexus_Backend.CareerNexus.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;

    @PostMapping("/post")
    public JobPost postJob(@RequestBody JobPost jobPost) {
        jobPost.setPostedAt(LocalDate.now());
        return jobPostService.createJobPost(jobPost);
    }

    @GetMapping("/recruiter")
    public List<JobPost> getJobsByRecruiter(@RequestParam String userId) {
        return jobPostService.getAllJobsByRecruiter(userId);
    }
}