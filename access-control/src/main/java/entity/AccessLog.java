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
    private String credential;  // RFID or PIN entered

    @Column(name = "door_id", nullable = false)
    private String doorId;

    @Column(nullable = false)
    private String decision;  // GRANT or DENY

    @Column(nullable = false)
    private String reason;    // Why granted or denied

    @Column(name = "user_id")
    private Long userId;      // If user was identified, store their ID

    @Column(name = "attempt_time", nullable = false)
    private LocalDateTime attemptTime;

    // Constructors
    public AccessLog() {}

    public AccessLog(String credential, String doorId, String decision, String reason, Long userId) {
        this.credential = credential;
        this.doorId = doorId;
        this.decision = decision;
        this.reason = reason;
        this.userId = userId;
        this.attemptTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCredential() { return credential; }
    public void setCredential(String credential) { this.credential = credential; }

    public String getDoorId() { return doorId; }
    public void setDoorId(String doorId) { this.doorId = doorId; }

    public String getDecision() { return decision; }
    public void setDecision(String decision) { this.decision = decision; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getAttemptTime() { return attemptTime; }
    public void setAttemptTime(LocalDateTime attemptTime) { this.attemptTime = attemptTime; }
}