package com.CareerNexus_Backend.CareerNexus.dto;

import java.util.List;

/**
 * Request DTO matching OneCompiler's expected structure.
 * This class is public and must reside in CodeExecutionRequestDto.java.
 */
public class CodeExecutionRequestDto {
    private String language;
    private String stdin;
    private List<CodeFile> files;

    public CodeExecutionRequestDto() {}

    public CodeExecutionRequestDto(String language, String stdin, List<CodeFile> files) {
        this.language = language;
        this.stdin = stdin;
        this.files = files;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStdin() {
        return stdin;
    }

    public void setStdin(String stdin) {
        this.stdin = stdin;
    }

    public List<CodeFile> getFiles() {
        return files;
    }

    public void setFiles(List<CodeFile> files) {
        this.files = files;
    }

    /**
     * Inner class representing an individual source file.
     */
    public static class CodeFile {
        private String name;
        private String content;

        public CodeFile() {}

        public CodeFile(String name, String content) {
            this.name = name;
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}