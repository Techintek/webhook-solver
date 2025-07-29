package com.example.webhooksolver.model;

// This class represents our final answer submission
public class SolutionRequest {
    private String finalQuery;  // Our SQL solution

    public SolutionRequest() {}

    public SolutionRequest(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    public String getFinalQuery() { return finalQuery; }
    public void setFinalQuery(String finalQuery) { this.finalQuery = finalQuery; }
}