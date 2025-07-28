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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public ApplicationDTO applyForJob(Long jobId, String studentUserId, MultipartFile resumeFile) throws Exception {

        JobPost jobPost = jobPostRepository.findById(jobId)
                .orElseThrow(() -> new Exception("JobPost not found with ID: " + jobId));


        User student = userAuthRepository.findById(studentUserId)
                .orElseThrow(() -> new Exception("Student User not found with ID: " + studentUserId));


        if (!"Student".equalsIgnoreCase(student.getRole())) {
            throw new IllegalArgumentException("Only users with 'STUDENT' role can apply for jobs.");
        }


        Optional<Application> existingApplication = applicationRepository.findByJobPost_IdAndStudent_UserId(jobId, studentUserId);
        if (existingApplication.isPresent()) {
            throw new IllegalArgumentException("Student has already applied for this job.");
        }


        String resumeUrl = null;
        if (resumeFile != null && !resumeFile.isEmpty()) {
            try {

                String originalFilename = resumeFile.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String uniqueFileName = jobId+"__"+studentUserId+"__"+UUID.randomUUID().toString() + "__"+ fileExtension;
                Path filePath = Paths.get(uploadDir).resolve(uniqueFileName);
                Files.copy(resumeFile.getInputStream(), filePath);
                resumeUrl = "/" + uniqueFileName;
            } catch (IOException e) {

                throw new RuntimeException("Failed to store resume file: " + e.getMessage(), e);
            }
        } else {
            throw new IllegalArgumentException("Resume file is required for application.");
        }
        Application application = new Application(jobPost, student, resumeUrl);
        return new ApplicationDTO(applicationRepository.save(application));
    }

    @Transactional()
    public ApplicationDTO getApplicationById(Long applicationId) throws Exception {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new Exception("Application not found with ID: " + applicationId));
        return new ApplicationDTO(application);
    }

    @Transactional()
    public List<ApplicationDTO> getApplicationsByStudent(String studentUserId) {
        List<Application> applications = applicationRepository.findByStudent_UserId(studentUserId);
        return applications.stream()
                .map(ApplicationDTO::new)
                .collect(Collectors.toList());
    }


    @Transactional()
    public List<ApplicationDTO> getApplicationsForJob(Long jobId) {
        List<Application> applications = applicationRepository.findByJobPost_Id(jobId);
        return applications.stream()
                .map(ApplicationDTO::new)
                .collect(Collectors.toList());
    }


    // it shows the total student applied for each individual job posted by specific recruiter
    @Transactional()
    public List<JobApplicationCountDTO> getApplicationCountsPerJobForRecruiter(String recruiterId) {
        User recruiter = userAuthRepository.findById(recruiterId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter user not found with ID: " + recruiterId));
        if (!"recruiter".equalsIgnoreCase(recruiter.getRole()) && !"tpo".equalsIgnoreCase(recruiter.getRole())) {
            throw new IllegalArgumentException("User ID " + recruiterId + " does not belong to a recruiter or TPO.");
        }
        return applicationRepository.countApplicationsWithDetailsPerJobForRecruiter(recruiter);
    }
 public JobApplicationCountDTO getCountById(Long id){
        return applicationRepository.countApplicationByJobId(id);
    }

    public List<StudentsApplicationsDTO> getStudentApplicationsForJobId(Long id) {
        return applicationRepository.findAllStudentsApplications(id);
    }


}
