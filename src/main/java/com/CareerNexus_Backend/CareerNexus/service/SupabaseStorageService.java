package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.config.SupabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
// CORRECT - add this
import org.springframework.http.HttpHeaders;

import java.io.IOException;



import java.util.UUID;

@Service
public class SupabaseStorageService {

    @Autowired
    private SupabaseConfig supabaseConfig;

    private final RestTemplate restTemplate = new RestTemplate();

    public String uploadFile(MultipartFile file) throws IOException {

        // 1. Generate unique file name to avoid conflicts
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // 2. Build the Supabase Storage upload URL
        // Format: /storage/v1/object/{bucket}/{filePath}
        String uploadUrl = supabaseConfig.getSupabaseUrl()
                + "/storage/v1/object/"
                + supabaseConfig.getBucket()
                + "/pdfs/" + uniqueFileName;

        // 3. Set headers — Authorization and Content-Type are mandatory
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseConfig.getSupabaseKey());
        headers.set("Content-Type", "application/pdf");
        headers.set("x-upsert", "false"); // don't overwrite if same name exists

        // 4. Build the request with file bytes as body
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

        // 5. Make the POST call to Supabase
        ResponseEntity<String> response = restTemplate.exchange(
                uploadUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // 6. If upload successful, build and return the public URL
        if (response.getStatusCode().is2xxSuccessful()) {
            return supabaseConfig.getSupabaseUrl()
                    + "/storage/v1/object/public/"
                    + supabaseConfig.getBucket()
                    + "/pdfs/" + uniqueFileName;
        }

        throw new RuntimeException("File upload to Supabase failed");
    }

    public void deleteFile(String fileUrl) {

        // Extract file path from the full URL
        String filePath = fileUrl.substring(fileUrl.indexOf("/pdfs/") + 1);

        String deleteUrl = supabaseConfig.getSupabaseUrl()
                + "/storage/v1/object/"
                + supabaseConfig.getBucket()
                + "/" + filePath;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseConfig.getSupabaseKey());

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        restTemplate.exchange(deleteUrl, HttpMethod.DELETE, requestEntity, String.class);
    }

    public String uploadResume(MultipartFile file, String jobId, String studentUserId) throws IOException {

        // Unique file name using jobId + studentUserId for easy identification
        String uniqueFileName = jobId + "__" + studentUserId + "__" + UUID.randomUUID().toString() + ".pdf";

        String uploadUrl = supabaseConfig.getSupabaseUrl()
                + "/storage/v1/object/"
                + supabaseConfig.getBucket()
                + "/resumes/" + uniqueFileName;  // stored in resumes/ folder

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseConfig.getSupabaseKey());
        headers.set("Content-Type", "application/pdf");
        headers.set("x-upsert", "false");

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uploadUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return supabaseConfig.getSupabaseUrl()
                    + "/storage/v1/object/public/"
                    + supabaseConfig.getBucket()
                    + "/resumes/" + uniqueFileName;
        }

        throw new RuntimeException("Resume upload to Supabase failed");
    }

    public void deleteResume(String fileUrl) {

        String filePath = fileUrl.substring(fileUrl.indexOf("/resumes/") + 1);

        String deleteUrl = supabaseConfig.getSupabaseUrl()
                + "/storage/v1/object/"
                + supabaseConfig.getBucket()
                + "/" + filePath;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseConfig.getSupabaseKey());

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        restTemplate.exchange(deleteUrl, HttpMethod.DELETE, requestEntity, String.class);
    }

}