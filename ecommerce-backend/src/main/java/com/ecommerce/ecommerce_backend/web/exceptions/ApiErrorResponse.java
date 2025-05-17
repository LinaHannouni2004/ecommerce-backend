package com.ecommerce.ecommerce_backend.web.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private List<String> errors;

    // Constructeurs
    public ApiErrorResponse(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ApiErrorResponse(int status, String message, LocalDateTime timestamp, List<String> errors) {
        this(status, message, timestamp);
        this.errors = errors;
    }

    // Getters
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<String> getErrors() {
        return errors;
    }
}