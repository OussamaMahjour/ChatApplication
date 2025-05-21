package com.app.userservice.DTO;

import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
    private Map<String, String> errors;
    // Getters and setters
}