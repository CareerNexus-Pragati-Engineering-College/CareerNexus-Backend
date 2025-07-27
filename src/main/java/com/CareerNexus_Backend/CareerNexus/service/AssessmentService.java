package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.AssessmentRoundDto;
import com.CareerNexus_Backend.CareerNexus.exceptions.ResourceNotFoundException;
import com.CareerNexus_Backend.CareerNexus.model.AssessmentRound;
import com.CareerNexus_Backend.CareerNexus.model.JobPost;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.AssessmentRepository;
import com.CareerNexus_Backend.CareerNexus.repository.JobPostRepository;
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service

public class AssessmentService {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private uploadHandler uploadFileHandler;

    private AssessmentRound assessmentRound;

    @Autowired
    private AssessmentRepository assessmentRepository;


    public AssessmentRound AssessmentConfiguration(AssessmentRoundDto assessmentRoundDto, MultipartFile questionPdf,MultipartFile answerPdf){

        User user = userAuthRepository.findById(assessmentRoundDto.getCreatedByUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + assessmentRoundDto.getCreatedByUserId()));
        JobPost jobPost=jobPostRepository.findById(assessmentRoundDto.getJobPostId())
                .orElseThrow(()->new ResourceNotFoundException("Jobpost not found with Id: "+assessmentRoundDto.getJobPostId()));
        String questionPdfUrl=uploadFileHandler.uploadFileHandler(questionPdf,assessmentRoundDto.getCreatedByUserId(),assessmentRoundDto.getJobPostId());

        String answerPdfUrl=uploadFileHandler.uploadFileHandler(answerPdf,assessmentRoundDto.getCreatedByUserId(),assessmentRoundDto.getJobPostId());

        if(questionPdfUrl==null || answerPdfUrl==null){
            return null;
        }

        assessmentRound=new AssessmentRound(user,jobPost, assessmentRoundDto.getRoundName(),assessmentRoundDto.getStartTime(),assessmentRoundDto.getEndTime(),questionPdfUrl,answerPdfUrl);


        return assessmentRepository.save(assessmentRound);
    }
}
