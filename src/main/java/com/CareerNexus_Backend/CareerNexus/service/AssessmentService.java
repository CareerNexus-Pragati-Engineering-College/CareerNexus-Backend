package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.AssessmentRoundDto;
import com.CareerNexus_Backend.CareerNexus.exceptions.ResourceNotFoundException;
import com.CareerNexus_Backend.CareerNexus.model.AssessmentRound;
import com.CareerNexus_Backend.CareerNexus.model.JobPost;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.AssessmentRepository;
import com.CareerNexus_Backend.CareerNexus.repository.JobPostRepository;
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository;
import com.CareerNexus_Backend.CareerNexus.security.EncryptionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AssessmentService {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private uploadHandler uploadFileHandler;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private QuestionExtractionService questionExtractionService;

    @Autowired
    private EncryptionService encryptionService;


    @Transactional
    public ResponseEntity<String> assessmentConfiguration(AssessmentRoundDto assessmentRoundDto, MultipartFile questionPdf) throws Exception {
        User user = userAuthRepository.findById(assessmentRoundDto.getCreatedByUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + assessmentRoundDto.getCreatedByUserId()));
        JobPost jobPost = jobPostRepository.findById(assessmentRoundDto.getJobPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Job post not found with Id: " + assessmentRoundDto.getJobPostId()));

        // Upload files and get their stored paths.
        String questionPdfPath = uploadFileHandler.uploadFileHandler(questionPdf, assessmentRoundDto.getCreatedByUserId(), assessmentRoundDto.getJobPostId());

        if (questionPdfPath == null ) {
            throw new RuntimeException("Failed to upload one or both PDF files.");
        }


        File pdfFile = new File("./static/uploads/assessment-files" + questionPdfPath);

        FileSystemResource fileResource = new FileSystemResource(pdfFile);
        System.out.println("requesting Gemini model to extract data from pdf...");
        String response=questionExtractionService.extractQuestionsFromFile(fileResource);
        System.out.println(response);


        /*  this method checks it the responses not equal to null then delete the file that temporary stored in assessment files.
       (Question pdf file).
        */

        if(response!=null){
            Files.deleteIfExists(pdfFile.toPath());
        }
        ObjectMapper objectMapper = new ObjectMapper();

        // 2. Parse the JSON string into a generic Map instead of DTOs eliminate unnecessary response by using substring concept
        Map<String, Object> extractedData = objectMapper.readValue(response.substring(7,response.length()-3), new TypeReference<>() {});

        // 3. Extract the questions and answers lists from the Map
        Object questionsData = extractedData.get("questions");
        Object answersData = extractedData.get("answers");

        // 4. Convert the separated lists back into their own JSON strings
        String questionsOnlyJson = objectMapper.writeValueAsString(questionsData);
        String answersOnlyJson = objectMapper.writeValueAsString(answersData);


        String encryptionKey=encryptionService.generateKey();

        AssessmentRound assessmentRound = new AssessmentRound(
                user,
                jobPost,
                assessmentRoundDto.getRoundName(),
                assessmentRoundDto.getMin_marks(),
                encryptionService.encrypt(response,encryptionKey),
                encryptionService.encrypt(answersOnlyJson,encryptionKey),
                encryptionKey,
                assessmentRoundDto.getStartTime(),
                assessmentRoundDto.getEndTime(),
                LocalDateTime.now()
        );
        assessmentRepository.save(assessmentRound);

        return ResponseEntity.ok("Round Configured Successfully...");

    }

    public List<AssessmentRoundDto> getAssessmentConfigurationData(String recruiterId, Long jobId) {
        return assessmentRepository.findAssessmentRoundsByJobId(jobId);
    }
}

