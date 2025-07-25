package com.CareerNexus_Backend.CareerNexus.controller;
/*
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseFileUploadService {

    public String uploadResume(MultipartFile file, String jobId, String studentId) throws IOException {
        String fileName = "resumes/" + jobId + "/" + studentId + "_" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

        // If you want a public URL:
        return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), blob.getName());
    }
}



 */