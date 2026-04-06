package com.smartlab.access_control.controller;

import com.smartlab.access_control.entity.BookingToken;
import com.smartlab.access_control.repository.BookingTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingTokenController {

    @Autowired
    private BookingTokenRepository bookingTokenRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @PostMapping("/token")
    public ResponseEntity<String> receiveBookingToken(@RequestBody Map<String, Object> payload) {
        try {
            // Extract data from payload
            String token = (String) payload.get("token");
            Long userId = Long.valueOf(payload.get("userId").toString());
            String userName = (String) payload.get("userName");
            Long roomId = Long.valueOf(payload.get("roomId").toString());
            String doorId = (String) payload.get("doorId");
            Long bookingId = Long.valueOf(payload.get("bookingId").toString());
            LocalDateTime validFrom = LocalDateTime.parse((String) payload.get("validFrom"), formatter);
            LocalDateTime validUntil = LocalDateTime.parse((String) payload.get("validUntil"), formatter);

            // Save to database
            BookingToken bookingToken = new BookingToken(
                    token, userId, userName, roomId, doorId,
                    bookingId, validFrom, validUntil
            );
            bookingTokenRepository.save(bookingToken);

            System.out.println("Received booking token: " + token + " for door: " + doorId);
            return ResponseEntity.ok("Token received and stored");

        } catch (Exception e) {
            System.err.println("Error processing token: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Booking API is working");
    }
}