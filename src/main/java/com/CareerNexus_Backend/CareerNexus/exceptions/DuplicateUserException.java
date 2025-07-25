package com.CareerNexus_Backend.CareerNexus.exceptions;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(int value, String message) {
        super(message);
    }
}
