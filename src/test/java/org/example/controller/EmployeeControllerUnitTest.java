package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTO.EmployeeDTO;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmployeeControllerUnitTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

        employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setGrossSalary(5000.0);
        // Call the method to calculate net salary
        employeeDTO.setNetSalary(5000 - (5000 * 0.15) - 500);  // Calculate net salary manually for test
    }

    @Test
        // Test for adding a new employee
    void testAddEmployee() throws Exception {
        when(employeeService.saveEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.netSalary").value(employeeDTO.getNetSalary()));

        verify(employeeService, times(1)).saveEmployee(any(EmployeeDTO.class));
    }

    @Test
        // Test for updating an existing employee
    void testUpdateEmployee() throws Exception {
        when(employeeService.saveEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(employeeService, times(1)).saveEmployee(any(EmployeeDTO.class));
    }

    @Test
        // Test for getting an employee by ID
    void testGetEmployee() throws Exception {
        when(employeeService.getEmployeeById(anyLong())).thenReturn(java.util.Optional.ofNullable(employeeDTO));

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(employeeService, times(1)).getEmployeeById(anyLong());
    }
}
