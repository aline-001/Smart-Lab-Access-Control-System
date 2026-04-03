package com.smartlab.access_control.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String rfidUid;

    @Column(nullable = false)
    private Boolean isFrequent = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public User() {}

    public User(String name, String rfidUid, Boolean isFrequent) {
        this.name = name;
        this.rfidUid = rfidUid;
        this.isFrequent = isFrequent;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRfidUid() { return rfidUid; }
    public void setRfidUid(String rfidUid) { this.rfidUid = rfidUid; }

    public Boolean getIsFrequent() { return isFrequent; }
    public void setIsFrequent(Boolean isFrequent) { this.isFrequent = isFrequent; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}