package com.CareerNexus_Backend.CareerNexus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Top-level DTO for parsing the full decrypted JSON from the AI service.
 * It contains both the questions and the corresponding answers.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractedDataDto {
    private List<QuestionDto> questions;
    private List<AnswerDto> answers;

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }
}