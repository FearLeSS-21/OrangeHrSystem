package org.example.service;

import org.example.model.DepartmentModel;
import org.example.model.EmployeeModel;
import org.example.model.TeamModel;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    public void testSaveEmployee() {
        DepartmentModel department = new DepartmentModel();
        department.setName("HR");
        departmentRepository.save(department);

        TeamModel team = new TeamModel();
        team.setName("Development");
        teamRepository.save(team);

        EmployeeModel employee = new EmployeeModel();
        employee.setName("John Doe");
        employee.setGender("Male");
        employee.setDateOfBirth(LocalDate.of(1990, 5, 10));
        employee.setGraduationDate(LocalDate.of(2012, 6, 15));
        employee.setDepartment(department);
        employee.setTeam(team);
        employee.setGrossSalary(5000.0);
        employee.calculateNetSalary();

        EmployeeModel savedEmployee = employeeRepository.save(employee);

        assertNotNull(savedEmployee.getId());
        assertEquals("John Doe", savedEmployee.getName());

    }

    @Test
    public void testGetAllEmployees() {
        DepartmentModel department = new DepartmentModel();
        department.setName("IT");
        departmentRepository.save(department);

        TeamModel team = new TeamModel();
        team.setName("Marketing");
        teamRepository.save(team);

        EmployeeModel employee = new EmployeeModel();
        employee.setName("Jane Smith");
        employee.setGender("Female");
        employee.setDateOfBirth(LocalDate.of(1985, 4, 12));
        employee.setGraduationDate(LocalDate.of(2008, 7, 30));
        employee.setDepartment(department);
        employee.setTeam(team);
        employee.setGrossSalary(6000.0);
        employee.calculateNetSalary();

        employeeRepository.save(employee);

        assertEquals(1, employeeService.getAllEmployees().size());
    }
}
