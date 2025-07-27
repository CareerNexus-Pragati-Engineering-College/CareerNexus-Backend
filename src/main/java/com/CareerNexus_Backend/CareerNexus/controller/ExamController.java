package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.dto.AssessmentRoundDto;
import com.CareerNexus_Backend.CareerNexus.model.AssessmentRound;
import com.CareerNexus_Backend.CareerNexus.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/exam")
public class ExamController {

    @Autowired
    private AssessmentService assessmentService;

    @PostMapping("/recruiter/mcq/assessment")
    public AssessmentRound AssessmentConfiguration(@RequestPart("roundDetails") AssessmentRoundDto assessmentRoundDto,@RequestPart("questionPdf") MultipartFile questionPdf,@RequestPart("answerPdf") MultipartFile answerPdf){
        return  assessmentService.AssessmentConfiguration(assessmentRoundDto,questionPdf,answerPdf);
    }

}
