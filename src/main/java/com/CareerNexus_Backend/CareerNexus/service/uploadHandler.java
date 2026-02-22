package com.CareerNexus_Backend.CareerNexus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class uploadHandler {

    @Value("${file.upload-assessment-files}")
    private String uploadDir;

    public String uploadFileHandler(MultipartFile file, String recruiterId, Long job_Id){

        String fileUrl=null;
        if (file != null && !file.isEmpty()) {
            try {

                String originalFilename = file.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String uniqueFileName = recruiterId+"__"+job_Id+"__"+ UUID.randomUUID().toString() + "__"+ fileExtension;
                Path filePath = Paths.get(uploadDir).resolve(uniqueFileName);
                Files.createDirectories(filePath.getParent());
                Files.copy(file.getInputStream(), filePath);
                fileUrl="/"+uniqueFileName;
            } catch (IOException e) {
                throw new RuntimeException("Failed to store assessment file: " + e.getMessage(), e);
            }
        }
        return fileUrl;
    }
}
