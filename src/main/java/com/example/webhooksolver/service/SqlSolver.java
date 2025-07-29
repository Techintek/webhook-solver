package com.example.webhooksolver.service;

import org.springframework.stereotype.Service;

// @Service tells Spring this is a business logic component
@Service
public class SqlSolver {

    // This method decides which SQL problem to solve
    public String solveProblem(String regNo) {
        // Get last two digits of registration number
        String lastTwoDigits = regNo.substring(regNo.length() - 2);
        int number = Integer.parseInt(lastTwoDigits);

        // Check if odd or even
        if (number % 2 == 1) {
            System.out.println("📝 Solving Question 1 (Odd registration)");
            return solveQuestion1();
        } else {
            System.out.println("📝 Solving Question 2 (Even registration)");
            return solveQuestion2();
        }
    }

    // SQL solution for Question 1 (Odd numbers)
    private String solveQuestion1() {
        return """
            SELECT e.employee_id, e.first_name, e.last_name, e.salary, e.department_id
            FROM employees e
            INNER JOIN (
                SELECT department_id, MAX(salary) as max_salary
                FROM employees
                GROUP BY department_id
            ) dept_max ON e.department_id = dept_max.department_id 
            AND e.salary = dept_max.max_salary
            ORDER BY e.department_id;
            """;
    }

    // SQL solution for Question 2 (Even numbers)
    private String solveQuestion2() {
        return """
            SELECT DISTINCT salary
            FROM employees e1
            WHERE 2 = (
                SELECT COUNT(DISTINCT salary)
                FROM employees e2
                WHERE e2.salary >= e1.salary
            );
            """;
    }
}