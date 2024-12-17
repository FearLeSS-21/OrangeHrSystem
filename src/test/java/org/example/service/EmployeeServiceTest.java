package org.example.service;

import org.example.DTO.EmployeeDTO;
import org.example.Mapper.EmployeeMapper;
import org.example.model.DepartmentModel;
import org.example.model.EmployeeModel;
import org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ActiveProfiles("test")
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private TeamService teamService;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private EmployeeDTO employeeDTO;
    private EmployeeModel employeeModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setGender("Male");
        employeeDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employeeDTO.setGraduationDate(LocalDate.of(2012, 5, 10));
        employeeDTO.setDepartmentId(1L);
        employeeDTO.setExpertise(Set.of("Java", "Spring"));
        employeeDTO.setGrossSalary(5000.0);

        employeeModel = new EmployeeModel();
        employeeModel.setName("John Doe");
        employeeModel.setGender("Male");
        employeeModel.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employeeModel.setGraduationDate(LocalDate.of(2012, 5, 10));
        employeeModel.setGrossSalary(5000.0);
    }

    @Test
    void saveEmployee_Success() {
        when(departmentService.getDepartmentById(1L)).thenReturn(Optional.of(new DepartmentModel()));
        when(employeeMapper.toModel(employeeDTO)).thenReturn(employeeModel);
        when(employeeRepository.save(employeeModel)).thenReturn(employeeModel);
        when(employeeMapper.toDTO(employeeModel)).thenReturn(employeeDTO);

        EmployeeDTO savedEmployee = employeeService.saveEmployee(employeeDTO);

        assertNotNull(savedEmployee);
        assertEquals("John Doe", savedEmployee.getName());
    }

    @Test
    void getAllEmployees_Success() {
        when(employeeRepository.findAll()).thenReturn(List.of(employeeModel));
        when(employeeMapper.toDTO(employeeModel)).thenReturn(employeeDTO);

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());
    }

    @Test
    void getEmployeeById_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeModel));
        when(employeeMapper.toDTO(employeeModel)).thenReturn(employeeDTO);

        Optional<EmployeeDTO> employee = employeeService.getEmployeeById(1L);

        assertTrue(employee.isPresent());
        assertEquals("John Doe", employee.get().getName());
    }
}
