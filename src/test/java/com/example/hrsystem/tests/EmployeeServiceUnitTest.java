package com.example.hrsystem.tests;

import com.example.hrsystem.models.Employee;
import com.example.hrsystem.repositories.EmployeeRepository;
import com.example.hrsystem.services.EmployeeService;
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

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee();
        employee.setName("John Doe");
        employee.setGrossSalary(5000);
        employee.setNetSalary(0); // will be calculated in the service
    }

    @Test
    void testAddEmployee() {
        // Mock repository behavior
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Call the service method
        Employee savedEmployee = employeeService.addEmployee(employee);

        // Validate the result
        assertEquals("John Doe", savedEmployee.getName());
        assertEquals(5000 - (5000 * 0.15) - 500, savedEmployee.getNetSalary());
        verify(employeeRepository, times(1)).save(any(Employee.class));  // Verify the save method was called
    }

    @Test
    void testUpdateEmployee() {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setName("Updated Name");
        updatedEmployee.setGrossSalary(6000);
        updatedEmployee.setNetSalary(6000 - (6000 * 0.15) - 500);

        // Mock repository behavior
        when(employeeRepository.findById(anyLong())).thenReturn(java.util.Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        // Call the service method
        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        // Validate the result
        assertEquals("Updated Name", result.getName());
        assertEquals(6000 - (6000 * 0.15) - 500, result.getNetSalary());
        verify(employeeRepository, times(1)).save(any(Employee.class));  // Verify the save method was called
    }

    @Test
    void testDeleteEmployee() {
        Employee employeeToDelete = new Employee();
        employeeToDelete.setId(1L);
        employeeToDelete.setName("John Doe");

        // Mock repository behavior
        when(employeeRepository.findById(anyLong())).thenReturn(java.util.Optional.of(employeeToDelete));

        // Call the service method
        employeeService.deleteEmployee(1L);

        // Verify the repository delete method was called
        verify(employeeRepository, times(1)).delete(employeeToDelete);
    }

    @Test
    void testDeleteEmployeeWithoutManager() {
        Employee employeeToDelete = new Employee();
        employeeToDelete.setId(1L);
        employeeToDelete.setManager(null);  // No manager

        // Mock repository behavior
        when(employeeRepository.findById(anyLong())).thenReturn(java.util.Optional.of(employeeToDelete));

        // Call the service method and expect an exception
        assertThrows(IllegalStateException.class, () -> {
            employeeService.deleteEmployee(1L);
        });

        verify(employeeRepository, times(0)).delete(any(Employee.class));  // Ensure delete was not called
    }
}
