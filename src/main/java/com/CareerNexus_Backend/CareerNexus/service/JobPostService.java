package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.model.JobPost;
import com.CareerNexus_Backend.CareerNexus.model.User; // Import User model
import com.CareerNexus_Backend.CareerNexus.repository.JobPostRepository;
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // For handling Optional returns from findById

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private UserAuthRepository userAuthRepository; // Inject UserRepository to fetch User details if needed

    public JobPost createJobPost(JobPost jobPost) {


        return jobPostRepository.save(jobPost);
    }


    public List<JobPost> getAllJobsByRecruiter(String recruiterUserId) {
        Optional<User> recruiterOptional = userAuthRepository.findById(recruiterUserId);

        if (recruiterOptional.isPresent()) {
            User recruiter = recruiterOptional.get();
            // Use the custom finder method from JobPostRepository
            return jobPostRepository.findByPostedBy(recruiter);
            // Alternatively, if you prefer using the userId directly in the repo:
            // return jobPostRepository.findByPostedBy_UserId(recruiterUserId);
        } else {
            // Handle case where recruiter user is not found (e.g., throw an exception)
            throw new RuntimeException("Recruiter user not found with ID: " + recruiterUserId);
        }
    }

    /**
     * Retrieves all available job posts.
     *
     * @return A list of all JobPost entities.
     */
    public List<JobPost> getAllJobs() {
        return jobPostRepository.findAll();
    }

    /**
     * Retrieves a single job post by its ID.
     *
     * @param id The ID of the job post.
     * @return An Optional containing the JobPost if found, or empty if not.
     */
    public Optional<JobPost> getJobPostById(Long id) {
        return jobPostRepository.findById(id);
    }

    // You might also want methods for updating and deleting job posts.
    // Example for update:
    public JobPost updateJobPost(Long id, JobPost updatedJobPost) {
        return jobPostRepository.findById(id).map(jobPost -> {
            jobPost.setCompanyName(updatedJobPost.getCompanyName());
            jobPost.setJobTitle(updatedJobPost.getJobTitle());
            jobPost.setSalaryPackage(updatedJobPost.getSalaryPackage());
            jobPost.setApplicationDeadline(updatedJobPost.getApplicationDeadline());
            jobPost.setLocations(updatedJobPost.getLocations());
            jobPost.setJobDescription(updatedJobPost.getJobDescription());
            jobPost.setRecruitmentProcess(updatedJobPost.getRecruitmentProcess());
            jobPost.setPostedBy(updatedJobPost.getPostedBy());
            // postedBy and postedAt typically aren't updated this way
            return jobPostRepository.save(jobPost);
        }).orElseThrow(() -> new RuntimeException("Job Post not found with ID: " + id));
    }

    // Example for delete:
    public boolean  deleteJobPost(Long id) {
        try{
        jobPostRepository.deleteById(id);
        return true;

        }
        catch (Exception e){
            return false;
        }
    }
}