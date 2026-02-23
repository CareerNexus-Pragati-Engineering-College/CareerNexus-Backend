package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.dto.AssessmentRoundDto;
import com.CareerNexus_Backend.CareerNexus.dto.AssessmentRoundStudentStatusDto;
import com.CareerNexus_Backend.CareerNexus.dto.StudentAnswerSubmissionDto;
import com.CareerNexus_Backend.CareerNexus.dto.StudentExamDTO;
import com.CareerNexus_Backend.CareerNexus.model.StudentExamAttempt;
import com.CareerNexus_Backend.CareerNexus.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exam")
public class ExamController {

    @Autowired
    private AssessmentService assessmentService;



    @PostMapping("/recruiter/mcq/assessment")
    public ResponseEntity<String> AssessmentConfiguration(@RequestPart("roundDetails") AssessmentRoundDto assessmentRoundDto, @RequestPart("questionPdf") MultipartFile questionPdf) throws Exception {
        System.out.println(assessmentRoundDto.getMin_marks());
        return  assessmentService.assessmentConfiguration(assessmentRoundDto,questionPdf);
    }

    @GetMapping("/recruiter/{job_id}")
    public List<AssessmentRoundDto> getAssessmentConfigurationData(Authentication authentication, @PathVariable Long job_id){
        String recruiterId = authentication.getName();
        return  assessmentService.getAssessmentConfigurationData(recruiterId,job_id);
    }

    @GetMapping("/student/{job_id}")
    public List<AssessmentRoundDto> getAssessmentConfigurationForJobId(Authentication authentication, @PathVariable Long job_id){
        String studentId = authentication.getName();
        return  assessmentService.getAssessmentConfigurationForJobId(job_id, studentId);
    }

    // ---------- Recruiter round results ----------

    @GetMapping("/recruiter/round/{assessmentId}/students/passed")
    public List<AssessmentRoundStudentStatusDto> getPassedStudentsForRound(@PathVariable Long assessmentId) {
        return assessmentService.getPassedStudentsForRound(assessmentId);
    }

    @GetMapping("/recruiter/round/{assessmentId}/students/failed")
    public List<AssessmentRoundStudentStatusDto> getFailedStudentsForRound(@PathVariable Long assessmentId) {
        return assessmentService.getFailedStudentsForRound(assessmentId);
    }

    @GetMapping("/recruiter/round/{assessmentId}/students/pending")
    public List<AssessmentRoundStudentStatusDto> getPendingStudentsForRound(@PathVariable Long assessmentId) {
        return assessmentService.getPendingStudentsForRound(assessmentId);
    }

    @GetMapping("/{assessmentId}/start")
    public ResponseEntity<StudentExamDTO> unlockQuestions(@PathVariable Long assessmentId) {
        try {
            // retrievalService returns a single StudentExamDTO object
            StudentExamDTO data = assessmentService.getQuestionsForStudent(assessmentId);

            // Return the single object directly in the response body
            return ResponseEntity.ok(data);

        } catch (RuntimeException e) {
            // For time-gate or business logic violations
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error decrypting exam");
        }
    }


    @PostMapping("/{assessmentId}/submit")
    public ResponseEntity<?> submitExam(
            @PathVariable Long assessmentId,
            Authentication authentication,
            @RequestBody List<StudentAnswerSubmissionDto> answers) {
        try {
            String studentId = authentication.getName();
            // The processSubmission logic in the service handles grading and status updates
            StudentExamAttempt attempt = assessmentService.processSubmission(assessmentId, studentId, answers);

            return ResponseEntity.ok(Map.of(
                    "message", "Exam submitted successfully",
                    "score", attempt.getScore(),
                    "totalQuestions", attempt.getTotalQuestions(),
                    "status", attempt.getResultStatus(),
                    "submittedAt", attempt.getSubmittedAt()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An internal error occurred during evaluation."));
        }
    }
}
