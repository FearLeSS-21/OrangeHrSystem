
package org.example.controller;

import org.example.DTO.DepartmentDTO;
import org.example.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest  // Load the full Spring application context
@AutoConfigureMockMvc  // Automatically configure MockMvc
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Inject MockMvc for HTTP request testing

    @MockBean
    private DepartmentService departmentService;  // Mock the DepartmentService bean

    @Test
    void testCreateDepartment() throws Exception {
        // Prepare a mock DepartmentDTO object
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName("HR Department");

        // Simulate service layer response
        when(departmentService.saveDepartment(departmentDTO)).thenReturn(departmentDTO);

        // Perform a POST request
        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"HR Department\"}"))
                .andExpect(status().isOk())  // Expect 200 OK
                .andExpect(jsonPath("$.name").value("HR Department"));  // Validate the name field
    }

    @Test
    void testGetAllDepartments() throws Exception {
        // Prepare a mock list of departments
        List<DepartmentDTO> departments = Arrays.asList(
                new DepartmentDTO("HR Department"),
                new DepartmentDTO("Engineering Department")
        );

        // Simulate service layer response
        when(departmentService.getAllDepartments()).thenReturn(departments);

        // Perform a GET request
        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())  // Expect 200 OK
                .andExpect(jsonPath("$.length()").value(2))  // Verify there are 2 departments
                .andExpect(jsonPath("$[0].name").value("HR Department"))  // Validate the first department
                .andExpect(jsonPath("$[1].name").value("Engineering Department"));  // Validate the second department
    }
}
