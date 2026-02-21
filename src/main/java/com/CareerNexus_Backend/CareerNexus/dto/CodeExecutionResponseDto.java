package com.CareerNexus_Backend.CareerNexus.dto;

/**
 * Response DTO to capture the execution results.
 * This class is public and must reside in CodeExecutionResponseDto.java.
 */
public class CodeExecutionResponseDto {
    private String status;
    private String stdout;
    private String stderr;
    private String exception;
    private Long executionTime;
    private Integer limitRemaining;

    public CodeExecutionResponseDto() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public Integer getLimitRemaining() {
        return limitRemaining;
    }

    public void setLimitRemaining(Integer limitRemaining) {
        this.limitRemaining = limitRemaining;
    }
}