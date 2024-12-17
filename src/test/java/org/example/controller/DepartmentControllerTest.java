package org.example.controller;

import org.example.DTO.DepartmentDTO;
import org.example.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentService departmentService;

    @Test
    void testCreateDepartment() throws Exception {
        String departmentName = "HR Department";

        mockMvc.perform(post("/departments").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"" + departmentName + "\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value(departmentName));

        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        assertTrue(departments.stream().anyMatch(d -> d.getName().equals(departmentName)));
    }

    @Test
    void testGetAllDepartments() throws Exception {
        departmentService.saveDepartment(new DepartmentDTO("HR Department"));
        departmentService.saveDepartment(new DepartmentDTO("Engineering Department"));

        mockMvc.perform(get("/departments")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[0].name").value("HR Department")).andExpect(jsonPath("$[1].name").value("Engineering Department"));
    }
}
