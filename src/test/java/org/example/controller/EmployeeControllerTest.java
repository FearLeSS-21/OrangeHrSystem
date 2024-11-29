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

import static org.hamcrest.Matchers.nullValue;
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
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();

        // Create a test department for employee assignment
        DepartmentModel testDepartment = new DepartmentModel();
        testDepartment.setName("Engineering");
        testDepartment = departmentRepository.save(testDepartment);

        testDepartmentId = testDepartment.getId();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "John Doe",
                            "gender": "Male",
                            "dateOfBirth": "1990-01-01",
                            "graduationDate": "2012-06-15",
                            "departmentId": 1,
                            "managerId": null,
                            "teamId": 1,
                            "expertise": ["Java", "Spring Boot"],
                            "grossSalary": 75000.0
                        }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-01-01"))
                .andExpect(jsonPath("$.graduationDate").value("2012-06-15"))
                .andExpect(jsonPath("$.departmentId").value(1))
                .andExpect(jsonPath("$.managerId").value(nullValue()))  // Ensure managerId is null
                .andExpect(jsonPath("$.teamId").value(1))
                .andExpect(jsonPath("$.expertise[0]").value("Java"))  // Check the expertise array
                .andExpect(jsonPath("$.expertise[1]").value("Spring Boot"))
                .andExpect(jsonPath("$.grossSalary").value(75000.0));
    }

    @Test
    public void testCreateEmployeeWithInvalidData() throws Exception {
        // Missing name field
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
                .andExpect(status().isBadRequest());

        // Invalid graduation date
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
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        // Create employee first
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

        // Create employee and extract the response
        String response = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Extract employee ID from the response
        String employeeId = response.split("\"id\":")[1].split(",")[0].trim();

        // Fetch employee by ID
        mockMvc.perform(get("/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.grossSalary").value(60000.0))
                .andExpect(jsonPath("$.departmentId").value(testDepartmentId.intValue()));
    }
}
