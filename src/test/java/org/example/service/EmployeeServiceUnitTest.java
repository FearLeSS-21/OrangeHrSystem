package org.example.service;

import org.example.DTO.EmployeeDTO;
import org.example.model.EmployeeModel;
import org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceUnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private EmployeeDTO employeeDTO;
    private EmployeeModel employeeModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doez");
        employeeDTO.setGrossSalary(5000.0);
        employeeDTO.setNetSalary(5000 - (5000 * 0.15) - 500);

        employeeModel = new EmployeeModel();
        employeeModel.setName("John Doez");
        employeeModel.setGrossSalary(5000.0);
        employeeModel.setNetSalary(5000 - (5000 * 0.15) - 500);
    }

    @Test
    void testAddEmployee() {
        when(employeeRepository.save(any(EmployeeModel.class))).thenReturn(employeeModel);

        EmployeeDTO savedEmployeeDTO = employeeService.saveEmployee(employeeDTO);

        assertEquals("John Doez", savedEmployeeDTO.getName());
        assertEquals(5000 - (5000 * 0.15) - 500, savedEmployeeDTO.getNetSalary());

        verify(employeeRepository, times(1)).save(any(EmployeeModel.class));
    }

    @Test
    void testUpdateEmployee() {
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setName("Updated Name");
        updatedEmployeeDTO.setGrossSalary(6000.0);
        updatedEmployeeDTO.setNetSalary(6000 - (6000 * 0.15) - 500);

        when(employeeRepository.findById(anyLong())).thenReturn(java.util.Optional.of(employeeModel));
        when(employeeRepository.save(any(EmployeeModel.class))).thenAnswer(invocation -> {
            EmployeeModel savedEmployee = invocation.getArgument(0);
            savedEmployee.setName(updatedEmployeeDTO.getName());
            savedEmployee.setGrossSalary(updatedEmployeeDTO.getGrossSalary());
            savedEmployee.setNetSalary(updatedEmployeeDTO.getNetSalary());
            return savedEmployee;
        });

        EmployeeDTO result = employeeService.saveEmployee(updatedEmployeeDTO);

        assertEquals("Updated Name", result.getName());
        assertEquals(6000 - (6000 * 0.15) - 500, result.getNetSalary());

        verify(employeeRepository, times(1)).save(any(EmployeeModel.class));
    }
}
