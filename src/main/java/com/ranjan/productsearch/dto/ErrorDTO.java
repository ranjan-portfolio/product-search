package com.ranjan.productsearch.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {
    private int status;           // HTTP status code, e.g., 404, 500
    private String error;         // Short error message or error type, e.g., "Not Found"
    private String message;       // Detailed error message for clients
    private String path;          // The request path where the error occurred (optional)
    private Instant timestamp;
}
