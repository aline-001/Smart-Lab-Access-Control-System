package com.smartlab.access_control.dto;

public class AccessRequestDTO {
    private String credential;  // RFID or PIN
    private String doorId;

    public AccessRequestDTO() {}

    public AccessRequestDTO(String credential, String doorId) {
        this.credential = credential;
        this.doorId = doorId;
    }

    public String getCredential() { return credential; }
    public void setCredential(String credential) { this.credential = credential; }

    public String getDoorId() { return doorId; }
    public void setDoorId(String doorId) { this.doorId = doorId; }
}