package com.example.webhooksolver.service;

import com.example.webhooksolver.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    // API URLs (the endpoints we need to call)
    private static final String GENERATE_URL =
            "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String SUBMIT_URL =
            "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";

    // Inject the SQL solver service
    @Autowired
    private SqlSolver sqlSolver;

    // RestTemplate is used to make HTTP requests
    private final RestTemplate restTemplate = new RestTemplate();

    // This method runs automatically when the application starts
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStartup() {
        System.out.println("🔄 Starting webhook process...");
        try {
            processWebhookFlow();
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Main process that handles everything
    private void processWebhookFlow() {
        // Step 1: Generate webhook
        System.out.println("📡 Step 1: Generating webhook...");
        WebhookResponse webhookResponse = generateWebhook();

        if (webhookResponse == null) {
            System.err.println("❌ Failed to generate webhook");
            return;
        }

        System.out.println("✅ Webhook generated successfully!");
        System.out.println("🔗 Webhook URL: " + webhookResponse.getWebhook());

        // Step 2: Solve the SQL problem
        System.out.println("🧮 Step 2: Solving SQL problem...");
        String regNo = "REG12347";  // Your registration number
        String sqlSolution = sqlSolver.solveProblem(regNo);
        System.out.println("✅ SQL solution prepared!");

        // Step 3: Submit the solution
        System.out.println("📤 Step 3: Submitting solution...");
        boolean success = submitSolution(sqlSolution, webhookResponse.getAccessToken());

        if (success) {
            System.out.println("🎉 SUCCESS! Solution submitted successfully!");
        } else {
            System.err.println("❌ Failed to submit solution");
        }
    }

    // Method to generate webhook
    private WebhookResponse generateWebhook() {
        try {
            // Create the request data
            WebhookRequest request = new WebhookRequest(
                    "John Doe",           // Your name
                    "REG12347",          // Your registration number
                    "john@example.com"   // Your email
            );

            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create HTTP request
            HttpEntity<WebhookRequest> httpRequest = new HttpEntity<>(request, headers);

            // Make the API call
            ResponseEntity<WebhookResponse> response = restTemplate.exchange(
                    GENERATE_URL,
                    HttpMethod.POST,
                    httpRequest,
                    WebhookResponse.class
            );

            return response.getBody();

        } catch (Exception e) {
            System.err.println("Error generating webhook: " + e.getMessage());
            return null;
        }
    }

    // Method to submit solution
    private boolean submitSolution(String sqlQuery, String accessToken) {
        try {
            // Create the solution request
            SolutionRequest request = new SolutionRequest(sqlQuery);

            // Set up headers with JWT token
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", accessToken);  // JWT token here

            // Create HTTP request
            HttpEntity<SolutionRequest> httpRequest = new HttpEntity<>(request, headers);

            // Make the API call
            ResponseEntity<String> response = restTemplate.exchange(
                    SUBMIT_URL,
                    HttpMethod.POST,
                    httpRequest,
                    String.class
            );

            System.out.println("📋 Server response: " + response.getBody());
            return response.getStatusCode().is2xxSuccessful();

        } catch (Exception e) {
            System.err.println("Error submitting solution: " + e.getMessage());
            return false;
        }
    }
}