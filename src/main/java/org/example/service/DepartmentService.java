package org.example.service;

import org.example.DTO.DepartmentDTO;
import org.example.model.DepartmentModel;
import org.example.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Convert entity to DTO
    private DepartmentDTO convertToDTO(DepartmentModel departmentModel) {
        return new DepartmentDTO(departmentModel.getId(), departmentModel.getName());
    }

    // Convert DTO to entity
    private DepartmentModel convertToEntity(DepartmentDTO departmentDTO) {
        return new DepartmentModel(departmentDTO.getId(), departmentDTO.getName());
    }

    // Create or update a department
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        DepartmentModel departmentModel = convertToEntity(departmentDTO);
        departmentModel = departmentRepository.save(departmentModel);
        return convertToDTO(departmentModel);
    }

    // Get all departments
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get department by ID
    public Optional<DepartmentDTO> getDepartmentById(Long id) {
        return departmentRepository.findById(id).map(this::convertToDTO);
    }

    // Delete department by ID
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
