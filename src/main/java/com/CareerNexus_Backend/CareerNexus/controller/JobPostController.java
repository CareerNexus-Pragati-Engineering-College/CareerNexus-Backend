package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.model.JobPost;
import com.CareerNexus_Backend.CareerNexus.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/job")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;

    @PostMapping("/post")
    public JobPost  postJob(@RequestBody JobPost jobPost) {
        jobPost.setPostedAt(LocalDate.now());
        return  jobPostService.createJobPost(jobPost);
    }

    @GetMapping("/{userId}")
    public List<JobPost> getJobsByRecruiter( @PathVariable String userId) {
        return jobPostService.getAllJobsByRecruiter(userId);
    }

    @GetMapping("/all-jobs")
    public List<JobPost> getAllJobs(){
        return jobPostService.getAllJobs();
    }

    @PutMapping("update/{jobId}")
    public JobPost updateJob(@PathVariable Long jobId,@RequestBody JobPost updatedJobPost){
        return (jobPostService.updateJobPost(jobId, updatedJobPost));
    }

    @DeleteMapping("/{job_id}")

    public boolean deleteJob(@PathVariable Long job_id)
    {
     return jobPostService.deleteJobPost(job_id);
    }
}