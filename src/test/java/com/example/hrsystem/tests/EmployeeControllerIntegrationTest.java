package com.example.hrsystem.tests;

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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")  // Ensures the test profile (with H2) is used
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
        employee = new EmployeeModel();
        employee.setName("John Doe");
        employee.setGrossSalary(5000);
        employee.setNetSalary(0); // will be calculated in the service
    }

    @Test
    void testAddEmployee() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee savedEmployee = employeeRepository.save(employee);
        savedEmployee.setName("Updated Name");

        mockMvc.perform(put("/api/employees/" + savedEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Name")));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        Employee savedEmployee = employeeRepository.save(employee);

        mockMvc.perform(delete("/api/employees/" + savedEmployee.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/employees/" + savedEmployee.getId()))
                .andExpect(status().isNotFound());
    }
}
