package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.JobPostDTO;
import com.CareerNexus_Backend.CareerNexus.dto.RecruiterDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.model.JobPost;
import com.CareerNexus_Backend.CareerNexus.repository.JobPostRepository;
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;


    @Transactional
    public JobPostDTO createJobPost(JobPostDTO jobPostDTO, String  userId) {


        JobPost jobPost = convertDtoToEntity(jobPostDTO);

        jobPost.setPostedBy(userAuthRepository.findByUserId(userId).get());
        jobPost.setPostedAt(LocalDate.now());


        JobPost savedJobPost = jobPostRepository.save(jobPost);


        return convertEntityToDto(savedJobPost);
    }

    @Transactional(readOnly = true) // Read-only transaction
    public List<JobPostDTO> getAllJobsByRecruiter(String recruiterUserId) { // recruiterUserId is Long

        List<JobPost> jobs = jobPostRepository.findByPostedBy(userAuthRepository.findByUserId(recruiterUserId).get());
        return jobs.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<JobPostDTO> getAllJobs() {
        List<JobPost> jobs = jobPostRepository.findAll();
        return jobs.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public JobPostDTO getJobPostById(Long jobId) throws Exception { // Changed parameter name to jobId for consistency
        JobPost jobPost = jobPostRepository.findById(jobId)
                .orElseThrow(() -> new Exception("Job Post not found with ID: " + jobId));
        return convertEntityToDto(jobPost);
    }

    @Transactional // Add transactional for write operations
    public JobPostDTO updateJobPost(Long jobId, JobPostDTO updatedJobPostDTO, String recruiterId) throws Exception { // Accepts DTO and recruiterId
        JobPost existingJobPost = jobPostRepository.findById(jobId)
                .orElseThrow(() -> new Exception("Job Post not found with ID: " + jobId));


        existingJobPost.setCompanyName(updatedJobPostDTO.getCompanyName());
        existingJobPost.setJobTitle(updatedJobPostDTO.getJobTitle());
        existingJobPost.setSalaryPackage(updatedJobPostDTO.getSalaryPackage());
        existingJobPost.setApplicationDeadline(updatedJobPostDTO.getApplicationDeadline());
        existingJobPost.setLocations(updatedJobPostDTO.getLocations());
        existingJobPost.setJobDescription(updatedJobPostDTO.getJobDescription());
        existingJobPost.setRecruitmentProcess(updatedJobPostDTO.getRecruitmentProcess());
        JobPost savedJobPost = jobPostRepository.save(existingJobPost);
        return convertEntityToDto(savedJobPost);
    }

    @Transactional
    public void deleteJobPost(Long jobId, String recruiterId) throws Exception {
        JobPost jobPost = jobPostRepository.findById(jobId)
                .orElseThrow(() -> new Exception("Job Post not found with ID: " + jobId));
        jobPostRepository.delete(jobPost);
    }

    private JobPost convertDtoToEntity(JobPostDTO dto) {

        JobPost jobPost = new JobPost();

        jobPost.setCompanyName(dto.getCompanyName());
        jobPost.setJobTitle(dto.getJobTitle());
        jobPost.setSalaryPackage(dto.getSalaryPackage());
        jobPost.setApplicationDeadline(dto.getApplicationDeadline());
        jobPost.setLocations(dto.getLocations());
        jobPost.setJobDescription(dto.getJobDescription());
        jobPost.setRecruitmentProcess(dto.getRecruitmentProcess());
        jobPost.setPostedAt(dto.getPostedAt());
        return jobPost;
    }

    private JobPostDTO convertEntityToDto(JobPost entity) {
        JobPostDTO dto = new JobPostDTO();
        dto.setId(entity.getId());
        dto.setCompanyName(entity.getCompanyName());
        dto.setJobTitle(entity.getJobTitle());
        dto.setSalaryPackage(entity.getSalaryPackage());
        dto.setApplicationDeadline(entity.getApplicationDeadline());
        dto.setLocations(entity.getLocations());
        dto.setJobDescription(entity.getJobDescription());
        dto.setRecruitmentProcess(entity.getRecruitmentProcess());
        dto.setPostedAt(entity.getPostedAt());

        // Assuming you want the recruiter's name in the DTO for display
        if (entity.getPostedBy() != null) {

            dto.setRecruiterDetails(new RecruiterDetailsDTO(entity.getPostedBy().getRecruiterDetails()));

        }
        return dto;
    }
}