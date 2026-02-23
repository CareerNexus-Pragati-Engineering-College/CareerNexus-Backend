package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.*;
import com.CareerNexus_Backend.CareerNexus.exceptions.ResourceNotFoundException;
import com.CareerNexus_Backend.CareerNexus.model.*;
import com.CareerNexus_Backend.CareerNexus.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CodingAssessmentService {

    @Autowired
    private CodingAssessmentRepository assessmentRepository;
    
    @Autowired
    private CodingQuestionRepository questionRepository;
    
    @Autowired
    private TestCaseRepository testCaseRepository;
    
    @Autowired
    private UserAuthRepository userRepository;
    
    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private CodingAssessmentResultRepository resultRepository;

    @Autowired
    private CodeExecutionService executionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Transactional
    public CodingAssessment createCodingAssessment(CodingAssessmentRequestDto requestDto, String tpoId) {
        
        User tpo = userRepository.findById(tpoId)
                .orElseThrow(() -> new ResourceNotFoundException("TPO User not found."));

        JobPost jobPost = null;
        if (requestDto.getJobPostId() != null) {
            jobPost = jobPostRepository.findById(requestDto.getJobPostId())
                    .orElseThrow(() -> new ResourceNotFoundException("JobPost not found."));
        }

        // 1. Create top-level Assessment
        CodingAssessment assessment = new CodingAssessment(
                requestDto.getAssessmentName(),
                tpo,
                jobPost,
                requestDto.getStartTime(),
                requestDto.getEndTime()
        );
        // Set mode (PRACTICE by default) and minimum marks if provided
        String mode = requestDto.getMode() != null ? requestDto.getMode() : "PRACTICE";
        assessment.setAssessmentMode(mode);
        assessment.setMinMarks(requestDto.getMinMarks());

        CodingAssessment savedAssessment = assessmentRepository.save(assessment);

        // 2. Loop and map Questions
        if (requestDto.getQuestions() != null) {
            for (CodingQuestionDto qDto : requestDto.getQuestions()) {
                CodingQuestion question = new CodingQuestion(
                        savedAssessment,
                        qDto.getTitle(),
                        qDto.getDescription(),
                        qDto.getConstraints(),
                        qDto.getPoints()
                );
                CodingQuestion savedQuestion = questionRepository.save(question);

                // 3. Loop and map Test Cases
                if (qDto.getTestCases() != null) {
                    for (TestCaseDto tcDto : qDto.getTestCases()) {
                        TestCase testCase = new TestCase(
                                savedQuestion,
                                tcDto.getInput(),
                                tcDto.getExpectedOutput(),
                                tcDto.getIsHidden(),
                                tcDto.getMarks()
                        );
                        testCaseRepository.save(testCase);
                    }
                }
            }
        }

        return savedAssessment;
    }

    @Transactional(readOnly = true)
    public StudentCodingAssessmentDto getStudentCodingAssessment(Long assessmentId, String studentId) {
        CodingAssessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Coding Assessment not found."));

        List<CodingQuestion> questions = questionRepository.findByCodingAssessmentId(assessmentId);
        
        List<StudentCodingQuestionDto> questionDtos = questions.stream().map(q -> {
            List<TestCase> publicTestCases = testCaseRepository.findByCodingQuestionId(q.getId())
                    .stream()
                    .filter(tc -> !tc.isHidden())
                    .collect(Collectors.toList());

            List<StudentTestCaseDto> publicTestCaseDtos = publicTestCases.stream()
                    .map(tc -> new StudentTestCaseDto(tc.getId(), tc.getInput(), tc.getExpectedOutput(), tc.getMarks()))
                    .collect(Collectors.toList());

            return new StudentCodingQuestionDto(
                    q.getId(),
                    q.getTitle(),
                    q.getDescription(),
                    q.getConstraints(),
                    q.getPoints(),
                    publicTestCaseDtos
            );
        }).collect(Collectors.toList());

        // Check if student already has a result for this assessment (practice history)
        CodingAssessmentResult result = resultRepository
                .findByStudent_UserIdAndAssessment_Id(studentId, assessmentId)
                .orElse(null);

        Boolean solved = result != null;
        Integer totalScore = result != null ? result.getTotalScore() : null;
        Integer maxScore = result != null ? result.getMaxScore() : null;

        return new StudentCodingAssessmentDto(
                assessment.getId(),
                assessment.getAssessmentName(),
                assessment.getJobPost() != null ? assessment.getJobPost().getId() : null,
                assessment.getStartTime().toString(),
                assessment.getEndTime().toString(),
                questionDtos,
                solved,
                totalScore,
                maxScore
        );
    }

    @Transactional
    public CodingAssessmentResult submitCodingAssessment(Long assessmentId, CodeSubmissionRequestDto submissionDto, String studentId) {
        CodingAssessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Coding Assessment not found."));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found."));

        int totalScore = 0;
        int maxScore = 0;

        for (CodeSubmissionRequestDto.QuestionSubmission sub : submissionDto.getSubmissions()) {
            CodingQuestion question = questionRepository.findById(sub.getQuestionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found."));
            
            maxScore += question.getPoints();

            List<TestCase> allTestCases = testCaseRepository.findByCodingQuestionId(question.getId());
            
            for (TestCase tc : allTestCases) {
                try {
                    CodeExecutionResponseDto response = executionService.executeCode(sub.getLanguage(), sub.getCode(), tc.getInput());
                    
                    String actualOutput = response.getStdout() != null ? response.getStdout().trim() : "";
                    String expectedOutput = tc.getExpectedOutput() != null ? tc.getExpectedOutput().trim() : "";

                    if (actualOutput.equals(expectedOutput)) {
                        totalScore += tc.getMarks();
                    }
                } catch (Exception e) {
                    System.err.println("Error executing test case " + tc.getId() + ": " + e.getMessage());
                }
            }
        }

        String submissionDataJson = "";
        try {
            submissionDataJson = objectMapper.writeValueAsString(submissionDto);
        } catch (Exception e) {
            submissionDataJson = "Error serializing submission data";
        }

        // --- LATEST CODE ONLY LOGIC ---
        // Check if a result already exists for this student and assessment
        CodingAssessmentResult result = resultRepository.findByStudent_UserIdAndAssessment_Id(studentId, assessmentId)
                .orElse(new CodingAssessmentResult(assessment, student, 0, 0, ""));

        result.setTotalScore(totalScore);
        result.setMaxScore(maxScore);
        result.setSubmissionData(submissionDataJson);
        result.setSubmittedAt(LocalDateTime.now());

        CodingAssessmentResult savedResult = resultRepository.save(result);

        // If this coding assessment is configured as an elimination round,
        // reject the student's job application when they don't meet minimum marks.
        if ("ELIMINATION".equalsIgnoreCase(assessment.getAssessmentMode())
                && assessment.getJobPost() != null
                && assessment.getMinMarks() != null
                && savedResult.getTotalScore() < assessment.getMinMarks()) {

            Application application = applicationRepository
                    .findByJobPost_IdAndStudent_UserId(
                            assessment.getJobPost().getId(),
                            studentId
                    ).orElse(null);

            if (application != null) {
                application.setStatus("REJECTED");
                application.setFeedback("Did not meet minimum marks in Coding Round");
                applicationRepository.save(application);
            }
        }

        return savedResult;
    }

    @Transactional(readOnly = true)
    public List<StudentAssessmentDashboardDto> getStudentAssessmentDashboard(String studentId) {
        List<CodingAssessment> assessments = assessmentRepository.findAllByOrderByCreatedAtDesc();
        List<StudentAssessmentDashboardDto> dashboard = new ArrayList<>();

        for (CodingAssessment assessment : assessments) {
            CodingAssessmentResult result = resultRepository.findByStudent_UserIdAndAssessment_Id(studentId, assessment.getId())
                    .orElse(null);

            dashboard.add(new StudentAssessmentDashboardDto(
                    assessment.getId(),
                    assessment.getAssessmentName(),
                    assessment.getJobPost() != null ? assessment.getJobPost().getId() : null,
                    assessment.getStartTime(),
                    assessment.getEndTime(),
                    result != null,
                    result != null ? result.getTotalScore() : null,
                    result != null ? result.getMaxScore() : null
            ));
        }

        return dashboard;
    }
}
