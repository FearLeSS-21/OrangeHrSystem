package com.example.hrsystem.tests;

import com.example.hrsystem.models.Employee;
import com.example.hrsystem.repositories.EmployeeRepository;
import com.example.hrsystem.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")  // Use test profile with H2 DB
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setName("John Doe");
        employee.setGrossSalary(5000);
        employee.setNetSalary(0); // Will be calculated
    }

    @Test
    void testAddEmployee() {
        Employee savedEmployee = employeeService.addEmployee(employee);
        assertNotNull(savedEmployee.getId(), "Employee ID should not be null");
        assertEquals("John Doe", savedEmployee.getName(), "Employee name should be John Doe");
    }

    @Test
    void testUpdateEmployee() {
        Employee savedEmployee = employeeService.addEmployee(employee);
        savedEmployee.setName("Updated Name");

        Employee updatedEmployee = employeeService.updateEmployee(savedEmployee.getId(), savedEmployee);

        assertEquals("Updated Name", updatedEmployee.getName(), "Employee name should be updated");
    }

    @Test
    void testDeleteEmployee() {
        Employee savedEmployee = employeeService.addEmployee(employee);
        Long employeeId = savedEmployee.getId();

        employeeService.deleteEmployee(employeeId);

        assertFalse(employeeRepository.findById(employeeId).isPresent(), "Employee should be deleted");
    }
}
