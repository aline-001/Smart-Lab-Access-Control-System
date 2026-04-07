package com.smartlab.access_control.controller;

import com.smartlab.access_control.entity.BookingToken;
import com.smartlab.access_control.repository.BookingTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingTokenRepository bookingTokenRepository;

    private final Random random = new Random();
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createBooking(@RequestBody Map<String, Object> request) {
        try {
            // Extract booking details from request (sent by main system UI)
            Long userId = Long.valueOf(request.get("userId").toString());
            String userName = (String) request.get("userName");
            Long roomId = Long.valueOf(request.get("roomId").toString());
            String doorId = (String) request.get("doorId");
            Long bookingId = Long.valueOf(request.get("bookingId").toString());
            String startTimeStr = (String) request.get("startTime");
            String endTimeStr = (String) request.get("endTime");

            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);

            // Generate a random 5-digit token (10000 to 99999)
            String token = String.valueOf(10000 + random.nextInt(90000));

            // Save token to database
            BookingToken bookingToken = new BookingToken(
                    token, userId, userName, roomId, doorId,
                    bookingId, startTime, endTime
            );
            bookingTokenRepository.save(bookingToken);

            // Return token to main system UI
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("token", token);
            response.put("message", "Booking created successfully");
            response.put("validFrom", startTime.toString());
            response.put("validUntil", endTime.toString());

            System.out.println("Created booking token: " + token + " for user: " + userName);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<Map<String, Object>> validateToken(@PathVariable String token, @RequestParam String doorId) {
        LocalDateTime now = LocalDateTime.now();
        var tokenOpt = bookingTokenRepository.findValidToken(token, doorId, now);

        Map<String, Object> response = new HashMap<>();

        if (tokenOpt.isPresent()) {
            BookingToken bt = tokenOpt.get();
            response.put("valid", true);
            response.put("message", "Token is valid");
            response.put("userId", bt.getUserId());
            response.put("userName", bt.getUserName());
            response.put("validUntil", bt.getValidUntil().toString());
        } else {
            response.put("valid", false);
            response.put("message", "Token is invalid or expired");
        }

        return ResponseEntity.ok(response);
    }
}