package com.smartlab.access_control.service;

import com.smartlab.access_control.entity.BookingToken;
import com.smartlab.access_control.repository.BookingTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AccessService {

    @Autowired
    private BookingTokenRepository bookingTokenRepository;

    public AccessDecision evaluateAccess(String credential, String doorId) {
        LocalDateTime now = LocalDateTime.now();

        var tokenOpt = bookingTokenRepository.findValidToken(credential, doorId, now);

        AccessDecision decision = new AccessDecision();
        decision.setCredential(credential);
        decision.setDoorId(doorId);
        decision.setRequestTime(now);

        if (tokenOpt.isPresent()) {
            BookingToken token = tokenOpt.get();
            decision.setAllowed(true);
            decision.setReason("GRANTED: Valid booking token");
            decision.setUserId(token.getUserId());
            decision.setUserName(token.getUserName());
            decision.setBookingId(token.getBookingId());
        } else {
            decision.setAllowed(false);
            decision.setReason("DENIED: No valid token found");
        }

        return decision;
    }

    public static class AccessDecision {
        private boolean allowed;
        private String reason;
        private String credential;
        private String doorId;
        private LocalDateTime requestTime;
        private Long userId;
        private String userName;
        private Long bookingId;

        // Getters
        public boolean isAllowed() { return allowed; }
        public String getReason() { return reason; }
        public String getCredential() { return credential; }
        public String getDoorId() { return doorId; }
        public LocalDateTime getRequestTime() { return requestTime; }
        public Long getUserId() { return userId; }
        public String getUserName() { return userName; }
        public Long getBookingId() { return bookingId; }

        // Setters
        public void setAllowed(boolean allowed) { this.allowed = allowed; }
        public void setReason(String reason) { this.reason = reason; }
        public void setCredential(String credential) { this.credential = credential; }
        public void setDoorId(String doorId) { this.doorId = doorId; }
        public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }
        public void setUserId(Long userId) { this.userId = userId; }
        public void setUserName(String userName) { this.userName = userName; }
        public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    }
}