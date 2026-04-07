package com.smartlab.access_control.controller;

import com.smartlab.access_control.dto.AccessRequestDTO;
import com.smartlab.access_control.dto.AccessResponseDTO;
import com.smartlab.access_control.entity.AccessLog;
import com.smartlab.access_control.entity.BookingToken;
import com.smartlab.access_control.repository.AccessLogRepository;
import com.smartlab.access_control.repository.BookingTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/access")
public class AccessController {

    @Autowired
    private BookingTokenRepository bookingTokenRepository;

    @Autowired
    private AccessLogRepository accessLogRepository;

    @PostMapping("/request")
    public ResponseEntity<AccessResponseDTO> requestAccess(@RequestBody AccessRequestDTO request) {

        LocalDateTime now = LocalDateTime.now();
        String credential = request.getCredential();
        String doorId = request.getDoorId();

        // Check if token is valid
        Optional<BookingToken> tokenOpt = bookingTokenRepository.findValidToken(credential, doorId, now);

        boolean allowed;
        String message;
        Long userId = null;
        String userName = null;

        if (tokenOpt.isPresent()) {
            BookingToken token = tokenOpt.get();
            allowed = true;
            message = "GRANTED: Valid token";
            userId = token.getUserId();
            userName = token.getUserName();
        } else {
            allowed = false;
            message = "DENIED: Invalid or expired token";
        }

        // Log the attempt
        AccessLog log = new AccessLog(credential, doorId, allowed ? "GRANT" : "DENY", message, userId);
        accessLogRepository.save(log);

        // Return response to ESP32
        AccessResponseDTO response = new AccessResponseDTO(allowed, message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("{\"status\":\"running\"}");
    }
}