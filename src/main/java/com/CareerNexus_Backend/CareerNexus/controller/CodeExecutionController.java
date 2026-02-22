package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.service.CodeExecutionService;
import com.CareerNexus_Backend.CareerNexus.dto.CodeExecutionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.util.Map;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/student/code")
@CrossOrigin(origins = "*")
public class CodeExecutionController {

    private static final int MAX_CODE_LENGTH = 50000; // 50KB max code
    private static final int MAX_STDIN_LENGTH = 10000; // 10KB max stdin
    private static final List<String> ALLOWED_LANGUAGES = Arrays.asList("javascript", "java", "python", "cpp", "c");

    @Autowired
    private CodeExecutionService executionService;

    /**
     * Endpoint for students to run code snippets during contests or practice.
     */
    @PostMapping("/run")
    public ResponseEntity<?> runCode(@RequestBody Map<String, String> payload) {
        String language = payload.get("language");
        String code = payload.get("code");
        String stdin = payload.getOrDefault("stdin", "");

        if (language == null || code == null) {
            return ResponseEntity.badRequest().body("Language and Code are required.");
        }

        if (!ALLOWED_LANGUAGES.contains(language.toLowerCase())) {
            return ResponseEntity.badRequest().body("Unsupported language.");
        }

        if (code.length() > MAX_CODE_LENGTH) {
            return ResponseEntity.badRequest().body("Code exceeds maximum allowed length of 50,000 characters.");
        }

        if (stdin.length() > MAX_STDIN_LENGTH) {
            return ResponseEntity.badRequest().body("Standard input exceeds maximum allowed length of 10,000 characters.");
        }

        try {
            CodeExecutionResponseDto result = executionService.executeCode(language, code, stdin);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}