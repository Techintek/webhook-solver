package com.example.webhooksolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This annotation tells Spring Boot this is the main class
@SpringBootApplication
public class WebhookSolverApplication {

    // This is where your application starts
    public static void main(String[] args) {
        SpringApplication.run(WebhookSolverApplication.class, args);
        System.out.println("🚀 Application started successfully!");
    }
}