package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.CodeExecutionRequestDto;
import com.CareerNexus_Backend.CareerNexus.dto.CodeExecutionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class CodeExecutionService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rapidapi.key}")
    private String apiKey;

    private static final String ONECOMPILER_URL = "https://onecompiler-apis.p.rapidapi.com/api/v1/run";
    private static final String RAPIDAPI_HOST = "onecompiler-apis.p.rapidapi.com";

    /**
     * Sends the code to OneCompiler via RapidAPI and returns the results.
     */
    public CodeExecutionResponseDto executeCode(String language, String code, String stdin) {
        // 1. Prepare the File object
        String extension = getExtension(language);
        CodeExecutionRequestDto.CodeFile file = new CodeExecutionRequestDto.CodeFile("index." + extension, code);

        // 2. Prepare the Request Body
        CodeExecutionRequestDto requestBody = new CodeExecutionRequestDto(
                language.toLowerCase(),
                stdin,
                Collections.singletonList(file)
        );

        // 3. Set Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-rapidapi-key", apiKey);
        headers.set("x-rapidapi-host", RAPIDAPI_HOST);

        HttpEntity<CodeExecutionRequestDto> entity = new HttpEntity<>(requestBody, headers);

        try {
            // 4. Make the POST Request
            return restTemplate.postForObject(ONECOMPILER_URL, entity, CodeExecutionResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Code execution failed: " + e.getMessage());
        }
    }

    private String getExtension(String language) {
        return switch (language.toLowerCase()) {
            case "python" -> "py";
            case "java" -> "java";
            case "cpp", "c++" -> "cpp";
            case "javascript" -> "js";
            default -> "txt";
        };
    }
}