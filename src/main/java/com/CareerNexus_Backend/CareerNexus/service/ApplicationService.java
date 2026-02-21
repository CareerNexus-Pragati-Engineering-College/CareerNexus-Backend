package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.ApplicationDTO;
import com.CareerNexus_Backend.CareerNexus.dto.JobApplicationCountDTO;
import com.CareerNexus_Backend.CareerNexus.dto.StudentsApplicationsDTO;
import com.CareerNexus_Backend.CareerNexus.exceptions.ResourceNotFoundException;
import com.CareerNexus_Backend.CareerNexus.model.Application;
import com.CareerNexus_Backend.CareerNexus.model.JobPost;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.ApplicationRepository;
import com.CareerNexus_Backend.CareerNexus.repository.JobPostRepository;
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private SupabaseStorageService supabaseStorageService;

    // ── Apply For Job ─────────────────────────────────────────────────────────

    @Transactional
    public ApplicationDTO applyForJob(Long jobId, String studentUserId,
                                      MultipartFile resumeFile) throws Exception {

        // Find job post
        JobPost jobPost = jobPostRepository.findById(jobId)
                .orElseThrow(() -> new Exception(
                        "JobPost not found with ID: " + jobId));

        // Find student user
        User student = userAuthRepository.findById(studentUserId)
                .orElseThrow(() -> new Exception(
                        "Student User not found with ID: " + studentUserId));

        // Only students can apply
        if (!"Student".equalsIgnoreCase(student.getRole())) {
            throw new IllegalArgumentException(
                    "Only users with 'STUDENT' role can apply for jobs.");
        }

        // Check if already applied
        Optional<Application> existingApplication = applicationRepository
                .findByJobPost_IdAndStudent_UserId(jobId, studentUserId);
        if (existingApplication.isPresent()) {
            throw new IllegalArgumentException(
                    "Student has already applied for this job.");
        }

        // Resume is required
        if (resumeFile == null || resumeFile.isEmpty()) {
            throw new IllegalArgumentException(
                    "Resume file is required for application.");
        }

        // Validate resume is PDF
        if (!"application/pdf".equals(resumeFile.getContentType())) {
            throw new IllegalArgumentException(
                    "Resume must be a PDF file.");
        }

        // Validate resume size — 5MB limit
        if (resumeFile.getSize() > 5L * 1024 * 1024) {
            throw new IllegalArgumentException(
                    "Resume file size must be under 5MB.");
        }

        // Upload resume to Supabase
        String resumeUrl = supabaseStorageService.uploadResume(
                resumeFile,
                jobId.toString(),
                studentUserId);

        // Save application with Supabase public URL
        Application application = new Application(jobPost, student, resumeUrl);
        return new ApplicationDTO(applicationRepository.save(application));
    }

    // ── Get Application By Id ─────────────────────────────────────────────────

    @Transactional
    public ApplicationDTO getApplicationById(Long applicationId) throws Exception {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new Exception(
                        "Application not found with ID: " + applicationId));
        return new ApplicationDTO(application);
    }

    // ── Get Applications By Student ───────────────────────────────────────────

    @Transactional
    public List<ApplicationDTO> getApplicationsByStudent(String studentUserId) {
        return applicationRepository.findByStudent_UserId(studentUserId)
                .stream()
                .map(ApplicationDTO::new)
                .collect(Collectors.toList());
    }

    // ── Get Applications For Job ──────────────────────────────────────────────

    @Transactional
    public List<ApplicationDTO> getApplicationsForJob(Long jobId) {
        return applicationRepository.findByJobPost_Id(jobId)
                .stream()
                .map(ApplicationDTO::new)
                .collect(Collectors.toList());
    }

    // ── Get Application Counts Per Job For Recruiter ──────────────────────────

    @Transactional
    public List<JobApplicationCountDTO> getApplicationCountsPerJobForRecruiter(
            String recruiterId) {

        User recruiter = userAuthRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruiter user not found with ID: " + recruiterId));

        if (!"recruiter".equalsIgnoreCase(recruiter.getRole())
                && !"tpo".equalsIgnoreCase(recruiter.getRole())) {
            throw new IllegalArgumentException(
                    "User ID " + recruiterId
                            + " does not belong to a recruiter or TPO.");
        }

        return applicationRepository
                .countApplicationsWithDetailsPerJobForRecruiter(recruiter);
    }

    // ── Get Count By Job Id ───────────────────────────────────────────────────

    public JobApplicationCountDTO getCountById(Long id) {
        return applicationRepository.countApplicationByJobId(id);
    }

    // ── Get Student Applications For Job ─────────────────────────────────────

    public List<StudentsApplicationsDTO> getStudentApplicationsForJobId(Long id) {
        return applicationRepository.findAllStudentsApplications(id);
    }

    // ── Get Count Of Applications By Student ─────────────────────────────────

    public JobApplicationCountDTO getCountApplicationByStudent(String userId) {
        return applicationRepository.findCount(userId);
    }
}