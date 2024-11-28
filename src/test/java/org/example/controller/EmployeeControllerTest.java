package org.example.controller;

import org.example.model.DepartmentModel;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Long testDepartmentId;

    @BeforeEach
    void setUp() {
        // Clean the Employee and Department database
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();

        // Create a test department
        DepartmentModel testDepartment = new DepartmentModel();
        testDepartment.setName("Engineering");
        testDepartment = departmentRepository.save(testDepartment);

        // Store the department ID for reference in the tests
        testDepartmentId = testDepartment.getId();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                        {
                            "name": "John Doe",
                            "gender": "Male",
                            "dateOfBirth": "1990-01-01",
                            "graduationDate": "2012-06-15",
                            "expertise": "Software Development",
                            "grossSalary": 60000.0,
                            "departmentId": %d
                        }
                        """, testDepartmentId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.grossSalary").value(60000.0))
                .andExpect(jsonPath("$.departmentId").value(testDepartmentId.intValue()));
    }

    @Test
    public void testCreateEmployeeWithInvalidData() throws Exception {
        // Test missing name
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                        {
                            "gender": "Male",
                            "dateOfBirth": "1990-01-01",
                            "graduationDate": "2012-06-15",
                            "expertise": "Software Development",
                            "grossSalary": 60000.0,
                            "departmentId": %d
                        }
                        """, testDepartmentId)))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request

        // Test invalid date format for graduationDate
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                        {
                            "name": "Jane Doe",
                            "gender": "Female",
                            "dateOfBirth": "1990-01-01",
                            "graduationDate": "invalid-date",
                            "expertise": "Software Development",
                            "grossSalary": 50000.0,
                            "departmentId": %d
                        }
                        """, testDepartmentId)))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        // Create and save a new employee
        String employeeJson = String.format("""
                        {
                            "name": "John Doe",
                            "gender": "Male",
                            "dateOfBirth": "1990-01-01",
                            "graduationDate": "2012-06-15",
                            "expertise": "Software Development",
                            "grossSalary": 60000.0,
                            "departmentId": %d
                        }
                        """, testDepartmentId);

        String response = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Extract the ID of the created employee from the response (assuming it's returned in the response)
        String employeeId = response.split(",")[0].split(":")[1].trim(); // Basic extraction (adjust as needed)

        // Perform a GET request to retrieve the employee by ID
        mockMvc.perform(get("/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.grossSalary").value(60000.0))
                .andExpect(jsonPath("$.departmentId").value(testDepartmentId.intValue()));
    }
}
