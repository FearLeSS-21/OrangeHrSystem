package org.example.controller;

import jakarta.transaction.Transactional;
import org.example.DTO.EmployeeDTO;
import org.example.DTO.DepartmentDTO;
import org.example.model.DepartmentModel;
import org.example.repository.DepartmentRepository;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentRepository departmentRepository;

    private EmployeeDTO employeeDTO;

    @BeforeEach
    public void setup() throws Exception {
        // Set up two departments using DepartmentDTO
        DepartmentDTO department1 = new DepartmentDTO("HR Department");
        DepartmentDTO department2 = new DepartmentDTO("Engineering");

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"HR Department\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("HR Department"));

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Engineering\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Engineering"));

        // Set up a sample EmployeeDTO to be used for tests
        employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setGender("Male");
        employeeDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employeeDTO.setGraduationDate(LocalDate.of(2012, 5, 15));
        employeeDTO.setDepartmentId(1L); // Assuming Department with ID 1 exists after creating departments
        employeeDTO.setManagerId(2L);
        employeeDTO.setTeamId(3L);

        Set<String> expertise = new HashSet<>();
        expertise.add("Java");
        expertise.add("Spring");
        employeeDTO.setExpertise(expertise);
        employeeDTO.setGrossSalary(50000.0);

        // Ensure that a Department with ID 2 exists
        if (!departmentRepository.existsById(2L)) {
            DepartmentModel department = new DepartmentModel();
            department.setId(2L);
            department.setName("Engineering");
            departmentRepository.save(department);
        }
    }

    @Test
    public void testCreateEmployee() throws Exception {
        // Test creating an employee
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"John Doe\", \"gender\": \"Male\", \"dateOfBirth\": \"1990-01-01\", \"graduationDate\": \"2012-05-15\", \"departmentId\": 1, \"managerId\": 2, \"teamId\": 3, \"expertise\": [\"Java\", \"Spring\"], \"grossSalary\": 50000.0 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-01-01"))
                .andExpect(jsonPath("$.graduationDate").value("2012-05-15"))
                .andExpect(jsonPath("$.departmentId").value(1))
                .andExpect(jsonPath("$.managerId").value(2))
                .andExpect(jsonPath("$.teamId").value(3))
                .andExpect(jsonPath("$.expertise").isArray())
                .andExpect(jsonPath("$.expertise[0]").value("Java"))
                .andExpect(jsonPath("$.expertise[1]").value("Spring"))
                .andExpect(jsonPath("$.grossSalary").value(50000.0));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        // Test getting all employees
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        // Create an employee first
        MvcResult result = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Jane Smith\", \"gender\": \"Female\", \"dateOfBirth\": \"1985-04-10\", \"graduationDate\": \"2007-05-20\", \"departmentId\": 2, \"managerId\": 3, \"teamId\": 4, \"expertise\": [\"Python\", \"Django\"], \"grossSalary\": 60000.0 }"))
                .andExpect(status().isOk())
                .andReturn();

        // Extract the employee ID from the response
        String response = result.getResponse().getContentAsString();
        Long employeeId = Long.parseLong(response.substring(response.indexOf("\"id\":") + 5, response.indexOf(",\"name\"")));

        // Now get the employee by ID
        mockMvc.perform(get("/employees/" + employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.name").value("Jane Smith"));
    }

    @Test
    public void testGetEmployeeByIdNotFound() throws Exception {
        // Test fetching an employee that does not exist
        mockMvc.perform(get("/employees/999"))
                .andExpect(status().isNotFound());
    }
}
