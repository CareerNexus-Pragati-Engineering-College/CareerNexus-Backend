package com.CareerNexus_Backend.CareerNexus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a single question.
 * Note that 'correct_answer' is included here for internal parsing,
 * but is stripped out before sending to the student.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDto {
    @JsonProperty("question_no")
    private String questionNo;

    @JsonProperty("question_text")
    private String questionText;

    private List<String> options;

    @JsonProperty("correct_answer")
    private String correctAnswer;

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}