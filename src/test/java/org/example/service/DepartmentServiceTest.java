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
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    private DepartmentDTO departmentDTO;
    private DepartmentModel departmentModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        departmentDTO = new DepartmentDTO("IT");
        departmentModel = new DepartmentModel(1L, "IT");
    }

    @Test
    void saveDepartment() {
        when(departmentMapper.toModel(departmentDTO)).thenReturn(departmentModel);
        when(departmentRepository.save(departmentModel)).thenReturn(departmentModel);
        when(departmentMapper.toDTO(departmentModel)).thenReturn(departmentDTO);

        var savedDepartment = departmentService.saveDepartment(departmentDTO);

        assertNotNull(savedDepartment);
        assertEquals("IT", savedDepartment.getName());
    }

    @Test
    void saveDepartmentFailure() {
        when(departmentMapper.toModel(departmentDTO)).thenReturn(departmentModel);
        when(departmentRepository.save(departmentModel)).thenThrow(new RuntimeException("Save failed"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            departmentService.saveDepartment(departmentDTO);
        });

        assertEquals("Save failed", exception.getMessage());
    }

    @Test
    void getAllDepartments() {
        var departments = List.of(new DepartmentModel(2L, "HR"), new DepartmentModel(3L, "Finance"));
        when(departmentRepository.findAll()).thenReturn(departments);
        when(departmentMapper.toDTO(any(DepartmentModel.class))).thenAnswer(invocation -> new DepartmentDTO(((DepartmentModel) invocation.getArgument(0)).getName()));

        var result = departmentService.getAllDepartments();

        assertEquals(2, result.size());
        assertEquals("HR", result.get(0).getName());
        assertEquals("Finance", result.get(1).getName());
        verify(departmentRepository).findAll();
    }

    @Test
    void getDepartmentByIdExists() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(departmentModel));

        var foundDepartment = departmentService.getDepartmentById(1L);

        assertTrue(foundDepartment.isPresent());
        assertEquals("IT", foundDepartment.get().getName());
        verify(departmentRepository).findById(1L);
    }

    @Test
    void getDepartmentByIdNotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        var foundDepartment = departmentService.getDepartmentById(1L);

        assertFalse(foundDepartment.isPresent());
        verify(departmentRepository).findById(1L);
    }
}
