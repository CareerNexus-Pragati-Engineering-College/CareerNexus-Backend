package com.CareerNexus_Backend.CareerNexus.service;
/*
import com.CareerNexus_Backend.CareerNexus.model.JobApplication;
import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.repository.JobApplicationRepository;
import com.CareerNexus_Backend.CareerNexus.repository.StudentRepository;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository applicationRepository;

    @Autowired
    private StudentRepository studentRepository;

    private final String BUCKET_NAME = "your-gcs-bucket-name";

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    public String applyToJob(Long jobId, String userId, MultipartFile resumeFile) throws IOException {
        // Fetch student details
        Optional<Student> student = studentRepository.findByUserId(userId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        // Upload resume to Google Cloud
        String objectName = "resumes/job-" + jobId + "/" + userId + ".pdf";
        BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, objectName)
                .setContentType("application/pdf")
                .build();

        storage.create(blobInfo, resumeFile.getBytes());

        String fileUrl = "https://storage.googleapis.com/" + BUCKET_NAME + "/" + objectName;

        // Save application data
        JobApplication app = new JobApplication();
        app.setJobId(jobId);
        app.setUserId(userId);
        app.setFirstName(student.get().getFirstName());
        app.setLastName(student.get().getLastName());
        app.setDepartment(student.get().getDepartment());
        app.setEmail(student.get().getEmail());
        app.setPhone(student.get().getPhone());
        app.setCgpa(student.get().getCGPA());
        app.setYear(student.get().getYear());
        app.setGraduationYear(student.get().getGraduationYear());
        app.setSkills(student.get().getSkills());
        app.setResumePath(fileUrl);

        applicationRepository.save(app);

        return "Application submitted successfully!";
    }
}

*/