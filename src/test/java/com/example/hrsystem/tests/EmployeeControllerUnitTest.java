package com.example.hrsystem.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.EmployeeController;
import org.example.model.EmployeeModel;
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
    private EmployeeModel employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

        employee = new Employee();
        employee.setName("John Doe");
        employee.setGrossSalary(5000);
        employee.setNetSalary(5000 - (5000 * 0.15) - 500);  // Calculate net salary
    }

    @Test
    void testAddEmployee() throws Exception {
        when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.netSalary").value(employee.getNetSalary()));

        verify(employeeService, times(1)).addEmployee(any(Employee.class));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee(anyLong(), any(Employee.class))).thenReturn(employee);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(employeeService, times(1)).updateEmployee(anyLong(), any(Employee.class));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(anyLong());

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).deleteEmployee(anyLong());
    }

    @Test
    void testGetEmployee() throws Exception {
        when(employeeService.getEmployee(anyLong())).thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(employeeService, times(1)).getEmployee(anyLong());
    }
}
