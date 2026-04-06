package com.smartlab.access_control.client;

import com.smartlab.access_control.dto.MainSystemValidationDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Component
public class MainSystemClient {

    private final RestTemplate restTemplate;

    @Value("${main.system.api.url:http://localhost:8081}")
    private String mainSystemUrl;

    @Value("${main.system.mock.enabled:true}")
    private boolean mockEnabled;

    public MainSystemClient() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Validate credential with main system
     * Returns validation result or mock if main system unavailable
     */
    public MainSystemValidationDTO validateCredential(String credential, String doorId) {

        // MOCK MODE: Use this when real APIs are not available
        if (mockEnabled) {
            return getMockValidationResponse(credential, doorId);
        }

        // REAL MODE: Call actual main system API
        try {
            String url = mainSystemUrl + "/api/validate-access";
            // TODO: Replace with actual API call when you have the spec
            // For now, return mock
            return getMockValidationResponse(credential, doorId);
        } catch (Exception e) {
            // If main system is down, deny access (fail secure)
            MainSystemValidationDTO fallback = new MainSystemValidationDTO();
            fallback.setValid(false);
            fallback.setMessage("Main system unavailable: " + e.getMessage());
            return fallback;
        }
    }

    /**
     * MOCK RESPONSE - Replace with real API call when available
     * This simulates what the main system will return
     */
    private MainSystemValidationDTO getMockValidationResponse(String credential, String doorId) {
        MainSystemValidationDTO response = new MainSystemValidationDTO();

        // Mock logic: PIN "1234" or RFID "RFID_123" works for door "LAB_101"
        boolean isValid = false;
        String message = "";
        Long userId = null;
        String userName = null;
        Long bookingId = null;
        Long roomId = null;

        if ("1234".equals(credential) || "RFID_123".equals(credential)) {
            if ("LAB_101".equals(doorId)) {
                isValid = true;
                message = "Valid booking found";
                userId = 1L;
                userName = "Test User";
                bookingId = 100L;
                roomId = 1L;
            } else {
                message = "Credential valid but wrong room";
            }
        } else if ("5678".equals(credential)) {
            message = "Booking expired";
        } else {
            message = "No valid booking found for this credential";
        }

        response.setValid(isValid);
        response.setMessage(message);
        response.setUserId(userId);
        response.setUserName(userName);
        response.setBookingId(bookingId);
        response.setRoomId(roomId);

        return response;
    }
}