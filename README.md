# Webhook SQL Solver

A Spring Boot application that automatically processes webhook requests and solves SQL problems.

## What it does:
1. Generates webhook on startup
2. Solves SQL problem based on registration number (REG12347 - Question 1)
3. Submits solution via JWT authenticated API

## How to run:
```bash
mvn clean package
java -jar target/webhook-solver-0.0.1-SNAPSHOT.jar
