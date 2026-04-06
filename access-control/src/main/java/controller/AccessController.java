package com.smartlab.access_control.controller;

import com.smartlab.access_control.dto.AccessRequestDTO;
import com.smartlab.access_control.dto.AccessResponseDTO;
import com.smartlab.access_control.entity.AccessLog;
import com.smartlab.access_control.repository.AccessLogRepository;
import com.smartlab.access_control.service.AccessService;
import com.smartlab.access_control.service.AccessService.AccessDecision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/access")
public class AccessController {

    @Autowired
    private AccessService accessService;

    @Autowired
    private AccessLogRepository accessLogRepository;

    @PostMapping("/request")
    public ResponseEntity<AccessResponseDTO> requestAccess(@RequestBody AccessRequestDTO request) {

        AccessDecision decision = accessService.evaluateAccess(
                request.getCredential(),
                request.getDoorId()
        );

        // Save to log
        AccessLog log = new AccessLog(
                request.getCredential(),
                request.getDoorId(),
                decision.isAllowed() ? "GRANT" : "DENY",
                decision.getReason(),
                decision.getUserId()
        );
        accessLogRepository.save(log);

        AccessResponseDTO response = new AccessResponseDTO(
                decision.isAllowed(),
                decision.getReason()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("{\"status\":\"running\"}");
    }
}