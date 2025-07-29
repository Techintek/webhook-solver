package com.example.webhooksolver.model;

// This class represents the response we get from webhook generation
public class WebhookResponse {
    private String webhook;      // The URL to submit our answer
    private String accessToken;  // The JWT token for authentication

    public WebhookResponse() {}

    // Getters and Setters
    public String getWebhook() { return webhook; }
    public void setWebhook(String webhook) { this.webhook = webhook; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}