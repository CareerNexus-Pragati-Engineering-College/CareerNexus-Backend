package com.CareerNexus_Backend.CareerNexus.config;


import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FirebaseConfig {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file, String fileName, String folderName ) throws IOException {
        BlobId blobId = BlobId.of("carrernexus-c413a.firebasestorage.app",folderName+"/"+ fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        InputStream inputStream = FirebaseConfig.class.getClassLoader().getResourceAsStream("carrernexus-c413a-firebase-adminsdk-fbsvc-5270e040c1.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, file.getBytes());
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/carrernexus-c413a.firebasestorage.app/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(folderName+"/"+fileName, StandardCharsets.UTF_8));
    }
}


