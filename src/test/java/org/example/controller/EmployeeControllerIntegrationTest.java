package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.EmployeeModel;
import org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeModel employee;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        employee = new EmployeeModel();
        employee.setName("John Doe");
        employee.setGrossSalary(5000.0);
        employee.setNetSalary(0.0);
    }

    @Test
        // Test for adding a new employee
    void testAddEmployee() throws Exception {
        mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee))).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
        // Test for updating an existing employee
    void testUpdateEmployee() throws Exception {
        EmployeeModel savedEmployee = employeeRepository.save(employee);
        savedEmployee.setName("Updated Name");

        mockMvc.perform(put("/api/employees/" + savedEmployee.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(savedEmployee))).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("Updated Name")));
    }
}
