package com.CareerNexus_Backend.CareerNexus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a single entry in the 'answers' array of the AI response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerDto {
    @JsonProperty("question_no")
    private String questionNo;

    @JsonProperty("correct_answer")
    private String correctAnswer;

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}