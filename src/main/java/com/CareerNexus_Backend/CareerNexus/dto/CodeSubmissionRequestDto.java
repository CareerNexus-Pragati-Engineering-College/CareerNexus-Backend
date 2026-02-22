package com.CareerNexus_Backend.CareerNexus.dto;

import java.util.List;

public class CodeSubmissionRequestDto {
    private List<QuestionSubmission> submissions;

    public CodeSubmissionRequestDto() {}

    public List<QuestionSubmission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<QuestionSubmission> submissions) {
        this.submissions = submissions;
    }

    public static class QuestionSubmission {
        private Long questionId;
        private String language;
        private String code;

        public QuestionSubmission() {}

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
