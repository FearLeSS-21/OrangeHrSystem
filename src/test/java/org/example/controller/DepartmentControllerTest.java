package org.example.controller;

import org.example.DTO.DepartmentDTO;
import org.example.service.DepartmentService;
import org.example.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        // Clean up the repository before each test to avoid test data contamination
        departmentRepository.deleteAll();
    }

    @Test
    void testCreateDepartment() throws Exception {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName("HR Department");

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"HR Department\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("HR Department"));
    }

    @Test
    void testGetAllDepartments() throws Exception {
        List<DepartmentDTO> departments = Arrays.asList(
                new DepartmentDTO("HR Department"),
                new DepartmentDTO("Engineering Department")
        );

        departmentService.saveDepartment(departments.get(0));
        departmentService.saveDepartment(departments.get(1));

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("HR Department"))
                .andExpect(jsonPath("$[1].name").value("Engineering Department"));
    }
}
