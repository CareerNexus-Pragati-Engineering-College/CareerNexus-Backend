package com.CareerNexus_Backend.CareerNexus.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class QuestionExtractionService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${fastapi.extract.url:http://localhost:8000/assessment-extract}")
    private String fastApiExtractUrl;

    public QuestionExtractionService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String extractQuestionsFromFile(Resource fileResource) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("assessment_data", fileResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(fastApiExtractUrl, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return parseAndValidateResponse(response.getBody());
            }

            System.err.println("FastAPI returned non-OK status: " + response.getStatusCode());
            return getMockResponse();

        } catch (Exception e) {
            System.err.println("Error calling FastAPI: " + e.getMessage());
            return getMockResponse();
        }
    }

    /**
     * Strict validation + cleaning
     */
    private String parseAndValidateResponse(String responseBody) {
        try {

            // Remove everything before first {
            int startIndex = responseBody.indexOf("{");
            if (startIndex > 0) {
                responseBody = responseBody.substring(startIndex);
            }

            JsonNode root = objectMapper.readTree(responseBody);

            if (!root.has("questions") || !root.has("answers")) {
                System.err.println("Invalid JSON structure from FastAPI");
                return getMockResponse();
            }

            ArrayNode questions = (ArrayNode) root.get("questions");
            ArrayNode validQuestions = objectMapper.createArrayNode();

            // 🔥 Filter invalid questions (less than 4 options)
            for (JsonNode q : questions) {
                JsonNode options = q.get("options");
                if (options != null && options.isArray() && options.size() >= 4) {
                    validQuestions.add(q);
                }
            }

            // Replace with cleaned list
            ((com.fasterxml.jackson.databind.node.ObjectNode) root)
                    .set("questions", validQuestions);

            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(root);

        } catch (Exception e) {
            System.err.println("JSON Parsing Error: " + e.getMessage());
            return getMockResponse();
        }
    }

    private String getMockResponse() {
        return """
        {
          "questions": [],
          "answers": []
        }
        """;
    }
}