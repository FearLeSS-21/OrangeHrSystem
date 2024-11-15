package org.example.service;

import org.example.DTO.EmployeeDTO;
import org.example.model.EmployeeModel;
import org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        // Initialize EmployeeDTO instead of EmployeeModel
        employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setGrossSalary(5000.0);
        employeeDTO.setNetSalary(0.0); // net salary is calculated in the service
    }

    @Test
    void testAddEmployee() {
        // Save the employee using the service, which returns EmployeeDTO
        EmployeeDTO savedEmployee = employeeService.saveEmployee(employeeDTO);

        // Validate the saved employee
        assertNotNull(savedEmployee.getId());
        assertEquals("John Doe", savedEmployee.getName());
        assertEquals(5000.0, savedEmployee.getGrossSalary());
        assertNotNull(savedEmployee.getNetSalary()); // net salary should be calculated automatically
    }

    @Test
    void testUpdateEmployee() {
        // Save the employee first
        EmployeeDTO savedEmployeeDTO = employeeService.saveEmployee(employeeDTO);

        // Update employee information
        savedEmployeeDTO.setName("Updated Name");

        // Update the employee using the service, which also returns EmployeeDTO
        EmployeeDTO updatedEmployeeDTO = employeeService.saveEmployee(savedEmployeeDTO); // Use saveEmployee to handle update as well

        // Validate the updated employee information
        assertEquals("Updated Name", updatedEmployeeDTO.getName());
    }
}
