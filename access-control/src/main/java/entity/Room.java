package com.smartlab.access_control.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;
    private String doorId;
    private LocalDateTime createdAt;

    public Room() {}
}