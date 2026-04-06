package com.smartlab.access_control.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String rfidUid;
    private Boolean isFrequent;
    private LocalDateTime createdAt;

    public User() {}
    // Add getters/setters if needed
}