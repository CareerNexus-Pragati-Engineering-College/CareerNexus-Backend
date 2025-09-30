package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.dto.AssessmentRoundDto;
import com.CareerNexus_Backend.CareerNexus.model.AssessmentRound;
import com.CareerNexus_Backend.CareerNexus.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/exam")
public class ExamController {

    @Autowired
    private AssessmentService assessmentService;

    @PostMapping("/recruiter/mcq/assessment")
    public ResponseEntity<String> AssessmentConfiguration(@RequestPart("roundDetails") AssessmentRoundDto assessmentRoundDto, @RequestPart("questionPdf") MultipartFile questionPdf) throws Exception {
        return  assessmentService.assessmentConfiguration(assessmentRoundDto,questionPdf);
    }

    @GetMapping("/recruiter/{recruiterId}/{job_id}")
    public List<AssessmentRoundDto> getAssessmentConfigurationData(@PathVariable String recruiterId, @PathVariable Long job_id){
        return  assessmentService.getAssessmentConfigurationData(recruiterId,job_id);
    }

}
