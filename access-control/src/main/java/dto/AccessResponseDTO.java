package com.smartlab.access_control.dto;

import java.time.LocalDateTime;

public class AccessResponseDTO {
    private boolean allowed;
    private String message;
    private LocalDateTime timestamp;

    public AccessResponseDTO() {}

    public AccessResponseDTO(boolean allowed, String message) {
        this.allowed = allowed;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public boolean isAllowed() { return allowed; }
    public void setAllowed(boolean allowed) { this.allowed = allowed; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}