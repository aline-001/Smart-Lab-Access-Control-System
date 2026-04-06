package com.smartlab.access_control.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_tokens")
public class BookingToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "door_id", nullable = false)
    private String doorId;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Column(name = "valid_from", nullable = false)
    private LocalDateTime validFrom;

    @Column(name = "valid_until", nullable = false)
    private LocalDateTime validUntil;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    public BookingToken() {}

    public BookingToken(String token, Long userId, String userName, Long roomId, String doorId,
                        Long bookingId, LocalDateTime validFrom, LocalDateTime validUntil) {
        this.token = token;
        this.userId = userId;
        this.userName = userName;
        this.roomId = roomId;
        this.doorId = doorId;
        this.bookingId = bookingId;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.receivedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getToken() { return token; }
    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
    public Long getRoomId() { return roomId; }
    public String getDoorId() { return doorId; }
    public Long getBookingId() { return bookingId; }
    public LocalDateTime getValidFrom() { return validFrom; }
    public LocalDateTime getValidUntil() { return validUntil; }
    public LocalDateTime getReceivedAt() { return receivedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setToken(String token) { this.token = token; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public void setDoorId(String doorId) { this.doorId = doorId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public void setValidFrom(LocalDateTime validFrom) { this.validFrom = validFrom; }
    public void setValidUntil(LocalDateTime validUntil) { this.validUntil = validUntil; }
    public void setReceivedAt(LocalDateTime receivedAt) { this.receivedAt = receivedAt; }
}