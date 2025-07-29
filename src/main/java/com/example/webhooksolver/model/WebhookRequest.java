package com.example.webhooksolver.model;

// This class represents the data we send to generate webhook
public class WebhookRequest {
    private String name;
    private String regNo;
    private String email;

    // Empty constructor (required for JSON)
    public WebhookRequest() {}

    // Constructor with parameters
    public WebhookRequest(String name, String regNo, String email) {
        this.name = name;
        this.regNo = regNo;
        this.email = email;
    }

    // Getters and Setters (required for JSON conversion)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}