// Updated SqlSolver.java
package com.example.webhooksolver.service;

import org.springframework.stereotype.Service;

@Service
public class SqlSolver {
    
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
    
    // SQL solution for Question 1 (Odd numbers) - Keep existing
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
    
    // SQL solution for Question 2 (Even numbers) - UPDATED SOLUTION
    private String solveQuestion2() {
        return """
            SELECT 
                e1.EMP_ID,
                e1.FIRST_NAME,
                e1.LAST_NAME,
                d.DEPARTMENT_NAME,
                COUNT(e2.EMP_ID) as YOUNGER_EMPLOYEES_COUNT
            FROM 
                EMPLOYEE e1
            INNER JOIN 
                DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID
            LEFT JOIN 
                EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT 
                AND e2.DOB > e1.DOB
            GROUP BY 
                e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME
            ORDER BY 
                e1.EMP_ID DESC;
            """;
    }
}