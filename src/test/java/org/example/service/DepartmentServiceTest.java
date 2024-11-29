package org.example.service;

import org.example.DTO.DepartmentDTO;
import org.example.Mapper.DepartmentMapper;
import org.example.model.DepartmentModel;
import org.example.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveDepartment() {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName("IT");

        DepartmentModel departmentModel = new DepartmentModel();
        departmentModel.setName("IT");

        when(departmentRepository.save(any(DepartmentModel.class))).thenReturn(departmentModel);

        DepartmentDTO savedDepartment = departmentService.saveDepartment(departmentDTO);

        assertNotNull(savedDepartment);
        assertEquals("IT", savedDepartment.getName());
        verify(departmentRepository, times(1)).save(any(DepartmentModel.class));
    }

    @Test
    public void testGetAllDepartments() {
        DepartmentModel department1 = new DepartmentModel();
        department1.setName("HR");
        DepartmentModel department2 = new DepartmentModel();
        department2.setName("Finance");

        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department1, department2));

        List<DepartmentDTO> departments = departmentService.getAllDepartments();

        assertNotNull(departments);
        assertEquals(2, departments.size());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    public void testGetDepartmentById() {
        DepartmentModel department = new DepartmentModel();
        department.setId(1L);
        department.setName("Operations");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Optional<DepartmentModel> foundDepartment = departmentService.getDepartmentById(1L);

        assertTrue(foundDepartment.isPresent());
        assertEquals("Operations", foundDepartment.get().getName());
        verify(departmentRepository, times(1)).findById(1L);
    }
}
