// Updated WebhookService.java
package com.example.webhooksolver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.webhooksolver.model.SolutionRequest;
import com.example.webhooksolver.model.WebhookRequest;
import com.example.webhooksolver.model.WebhookResponse;

@Service
public class WebhookService {
    
    // API URLs
    private static final String GENERATE_URL = 
        "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String SUBMIT_URL = 
        "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
    
    @Autowired
    private SqlSolver sqlSolver;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
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
        
        // CHANGE THIS TO YOUR ACTUAL REGISTRATION NUMBER
        // Use even number to test Question 2, odd for Question 1
        String regNo = "REG12348";  // Even number for Question 2
        // String regNo = "REG12347";  // Odd number for Question 1
        
        String sqlSolution = sqlSolver.solveProblem(regNo);
        System.out.println("✅ SQL solution prepared!");
        System.out.println("📝 Generated SQL Query:");
        System.out.println(sqlSolution);
        
        // Step 3: Submit the solution
        System.out.println("📤 Step 3: Submitting solution...");
        boolean success = submitSolution(sqlSolution, webhookResponse.getAccessToken());
        
        if (success) {
            System.out.println("🎉 SUCCESS! Solution submitted successfully!");
        } else {
            System.err.println("❌ Failed to submit solution");
        }
    }
    
    private WebhookResponse generateWebhook() {
        try {
            WebhookRequest request = new WebhookRequest(
                "Taksh Joshi",                    
                "2210990884",                   
                "taksh884.be22@chitkara.edu.in"       
            );
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<WebhookRequest> httpRequest = new HttpEntity<>(request, headers);
            
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
    
    private boolean submitSolution(String sqlQuery, String accessToken) {
        try {
            SolutionRequest request = new SolutionRequest(sqlQuery);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", accessToken);
            
            HttpEntity<SolutionRequest> httpRequest = new HttpEntity<>(request, headers);
            
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