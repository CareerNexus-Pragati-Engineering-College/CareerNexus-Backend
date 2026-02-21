package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.service.CodeExecutionService;
import com.CareerNexus_Backend.CareerNexus.dto.CodeExecutionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/student/code")
@CrossOrigin(origins = "*")
public class CodeExecutionController {

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

        try {
            CodeExecutionResponseDto result = executionService.executeCode(language, code, stdin);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}