package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.*;
import com.CareerNexus_Backend.CareerNexus.exceptions.ResourceNotFoundException;
import com.CareerNexus_Backend.CareerNexus.model.*;
import com.CareerNexus_Backend.CareerNexus.repository.*;
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
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private  AssessmentNotificationService assessmentNotificationService;

    @Autowired
    private StudentExamAttemptRepository studentExamAttempt;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
        Map<String, Object> extractedData =
                objectMapper.readValue(response, new TypeReference<>() {});

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
                encryptionService.encrypt(questionsOnlyJson,encryptionKey),
                encryptionService.encrypt(answersOnlyJson,encryptionKey),
                encryptionKey,
                assessmentRoundDto.getStartTime(),
                assessmentRoundDto.getEndTime(),
                LocalDateTime.now()
        );
        assessmentRepository.save(assessmentRound);

        assessmentNotificationService.notifyRegisteredStudents(jobPost.getId(),jobPost.getJobTitle(),  assessmentRoundDto.getRoundName(),assessmentRoundDto.getStartTime().toString(),assessmentRoundDto.getEndTime().toString() );


        return ResponseEntity.ok("Round Configured Successfully...");

    }

    public List<AssessmentRoundDto> getAssessmentConfigurationData(String recruiterId, Long jobId) {
        System.out.println(recruiterId);
        return assessmentRepository.findAssessmentRoundsByJobId(jobId);
    }

    public List<AssessmentRoundDto> getAssessmentConfigurationForJobId(Long jobId) {
        List<AssessmentRoundDto> rounds =
                assessmentRepository.findConfigurationByJobId(jobId);

        // Set min_marks to 0 for each round for student
        rounds.forEach(round -> round.setMin_marks(0));

        return rounds;
    }

    public StudentExamDTO getQuestionsForStudent(Long assessmentId) throws Exception {
        // 1. Fetch from DB
        Optional<AssessmentRound> assessment = assessmentRepository.findById(assessmentId);

        // 2. Security Check: Is the exam active?
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(assessment.get().getStartTime())) {
            throw new RuntimeException("Exam has not started yet.");
        }
        if (now.isAfter(assessment.get().getEndTime())) {
            throw new RuntimeException("Exam window has closed.");
        }


        // 3. Decrypt the questions from the secure storage
        // Note: encryptionService.decrypt expects (data, key)
        String decryptedJson = encryptionService.decrypt(
                assessment.get().getEncryptedQuestions(),
                assessment.get().getEncryptionKey()
        );

        // 4. Use TypeReference to parse the JSON Array [...]
        List<QuestionDto> rawQuestions = objectMapper.readValue(
                decryptedJson,
                new TypeReference<List<QuestionDto>>() {}
        );

        // 5. THE CRITICAL STEP: Extract only Question data fields (Answer Stripping)
        List<QuestionDto> questionsOnly = rawQuestions.stream()
                .map(q -> {
                    QuestionDto stripped = new QuestionDto();
                    stripped.setQuestionNo(q.getQuestionNo());
                    stripped.setQuestionText(q.getQuestionText());
                    stripped.setOptions(q.getOptions());
                    // Ensure correct_answer is never set
                    return stripped;
                })
                .collect(Collectors.toList());

        // Log for verification
        System.out.println("Questions dispatched for assessment " + assessmentId + ": " + questionsOnly.size() + " items.");

        // 6. FIX: Wrap the list into a StudentExamDto object to prevent ClassCastException
        StudentExamDTO response=new StudentExamDTO();

        response.setAssessmentId(assessment.get().getId());
        response.setRoundName(assessment.get().getRoundName());
        response.setQuestions(questionsOnly);
        response.setEndTime(assessment.get().getEndTime());

        return response;
    }


    @Transactional
    public StudentExamAttempt processSubmission(Long assessmentId, String student, List<StudentAnswerSubmissionDto> studentAnswers) throws Exception {
        User user = userAuthRepository.findById(student)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " ));

        AssessmentRound assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        // 1. Decrypt Ground Truth (Answers) from its separate column
        String decryptedAnswers = encryptionService.decrypt(assessment.getEncryptedAnswers(), assessment.getEncryptionKey());
        List<AnswerDto> correctAnswers = objectMapper.readValue(decryptedAnswers, new TypeReference<List<AnswerDto>>() {});

        // Map for fast lookup: QuestionNo -> Correct String
        Map<String, String> truthMap = correctAnswers.stream()
                .collect(Collectors.toMap(AnswerDto::getQuestionNo, AnswerDto::getCorrectAnswer));

        // 2. BLOCKING LOGIC: Check if an attempt already exists
        if(studentExamAttempt.findByStudentAndAssessmentRound(user, assessment).isPresent()) {
            throw new RuntimeException("Access Denied: You have already submitted this assessment.");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(assessment.getStartTime())) {
            throw new RuntimeException("The exam window opens at: " + assessment.getStartTime());
        }
        if (now.isAfter(assessment.getEndTime())) {
            throw new RuntimeException("The exam window has already closed.");
        }

        // 2. Evaluation Logic
        int score = 0;
        for (StudentAnswerSubmissionDto sub : studentAnswers) {
            String correct = truthMap.get(String.valueOf(sub.getQueNo()));
            if (correct != null && correct.trim().equalsIgnoreCase(sub.getOption().trim())) {
                score++;
            }
        }

        // 3. Status Determination based on min_marks defined in AssessmentRound
        boolean isPassed = score >= assessment.getMin_marks();
        String resultStatus = isPassed ? "PASSED" : "FAILED";

        // 4. Save Attempt Record (using your Model from the Canvas)
        StudentExamAttempt attempt = new StudentExamAttempt();
        attempt.setStudent(user);
        attempt.setAssessmentRound(assessment);
        attempt.setScore(score);
        attempt.setTotalQuestions(correctAnswers.size());
        attempt.setResultStatus(resultStatus);
        attempt.setSubmittedAt(LocalDateTime.now());
        attempt.setStudentAnswersJson(objectMapper.writeValueAsString(studentAnswers));

        StudentExamAttempt savedAttempt = studentExamAttempt.save(attempt);

        // 5. Integration with main Application Status
        // If they fail the assessment, we reject their overall job application.
        if (!isPassed) {
            Application application =applicationRepository .findByJobPost_IdAndStudent_UserId(
                    assessment.getJobPost().getId(),
                    student
            ).orElse(null);

            if (application != null) {
                application.setStatus("REJECTED");
                application.setFeedback("Did not meet minimum marks in " + assessment.getRoundName());
                applicationRepository.save(application);
            }
        }

        return savedAttempt;
    }
}