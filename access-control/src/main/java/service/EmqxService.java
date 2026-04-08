package com.smartlab.access_control.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartlab.access_control.entity.BookingToken;
import com.smartlab.access_control.repository.BookingTokenRepository;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmqxService {

    @Autowired
    private BookingTokenRepository bookingTokenRepository;

    @Value("${mqtt.broker.url:ssl://v290163b.ala.us-east-1.emqssl.com:8883}")
    private String brokerUrl;

    @Value("${mqtt.broker.username:}")
    private String username;

    @Value("${mqtt.broker.password:}")
    private String password;

    @Value("${mqtt.topics.booking:darho/bookings/#}")
    private String bookingTopic;

    private MqttClient mqttClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @PostConstruct
    public void connect() {
        try {
            String clientId = "smart_bridge_" + System.currentTimeMillis();
            mqttClient = new MqttClient(brokerUrl, clientId);

            MqttConnectOptions options = new MqttConnectOptions();
            if (username != null && !username.isEmpty()) {
                options.setUserName(username);
                options.setPassword(password.toCharArray());
            }
            options.setCleanSession(true);

            mqttClient.connect(options);
            System.out.println("✅ Connected to EMQX at: " + brokerUrl);

            mqttClient.subscribe(bookingTopic, (topic, msg) -> {
                String payload = new String(msg.getPayload());
                System.out.println("📨 Received MQTT message: " + payload);
                processBookingMessage(payload);
            });

            System.out.println("📡 Subscribed to: " + bookingTopic);

        } catch (MqttException e) {
            System.err.println("❌ Failed to connect to EMQX: " + e.getMessage());
        }
    }

    private void processBookingMessage(String json) {
        try {
            JsonNode node = objectMapper.readTree(json);

            String token = node.get("token").asText();
            Long userId = node.get("userId").asLong();
            String userName = node.get("userName").asText();
            Long roomId = node.get("roomId").asLong();
            String doorId = node.get("doorId").asText();
            Long bookingId = node.get("bookingId").asLong();
            LocalDateTime validFrom = LocalDateTime.parse(node.get("validFrom").asText(), formatter);
            LocalDateTime validUntil = LocalDateTime.parse(node.get("validUntil").asText(), formatter);

            BookingToken bookingToken = new BookingToken(
                    token, userId, userName, roomId, doorId,
                    bookingId, validFrom, validUntil
            );
            bookingTokenRepository.save(bookingToken);

            System.out.println("💾 Saved token via MQTT: " + token + " for door: " + doorId);

        } catch (Exception e) {
            System.err.println("❌ Error processing MQTT message: " + e.getMessage());
        }
    }

    @PreDestroy
    public void disconnect() {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                mqttClient.disconnect();
                System.out.println("🔌 Disconnected from EMQX");
            }
        } catch (MqttException e) {
            System.err.println("❌ Error disconnecting: " + e.getMessage());
        }
    }
}