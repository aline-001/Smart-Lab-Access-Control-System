package com.smartlab.access_control.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_logs")
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String credential;

    @Column(name = "door_id", nullable = false)
    private String doorId;

    @Column(nullable = false)
    private String decision;

    @Column(nullable = false)
    private String reason;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "attempt_time", nullable = false)
    private LocalDateTime attemptTime;

    public AccessLog() {}

    public AccessLog(String credential, String doorId, String decision, String reason, Long userId) {
        this.credential = credential;
        this.doorId = doorId;
        this.decision = decision;
        this.reason = reason;
        this.userId = userId;
        this.attemptTime = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getCredential() { return credential; }
    public String getDoorId() { return doorId; }
    public String getDecision() { return decision; }
    public String getReason() { return reason; }
    public Long getUserId() { return userId; }
    public LocalDateTime getAttemptTime() { return attemptTime; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setCredential(String credential) { this.credential = credential; }
    public void setDoorId(String doorId) { this.doorId = doorId; }
    public void setDecision(String decision) { this.decision = decision; }
    public void setReason(String reason) { this.reason = reason; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setAttemptTime(LocalDateTime attemptTime) { this.attemptTime = attemptTime; }
}