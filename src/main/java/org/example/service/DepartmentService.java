package org.example.service;

import org.example.DTO.DepartmentDTO;
import org.example.model.DepartmentModel;
import org.example.repository.DepartmentRepository;
import org.example.Exception.ResourceNotFoundException;
import org.example.Exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    private DepartmentDTO convertToDTO(DepartmentModel departmentModel) {
        return new DepartmentDTO(departmentModel.getId(), departmentModel.getName());
    }

    private DepartmentModel convertToEntity(DepartmentDTO departmentDTO) {
        return new DepartmentModel(departmentDTO.getId(), departmentDTO.getName());
    }

    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        if (departmentDTO == null || departmentDTO.getName().isEmpty()) {
            throw new BadRequestException("Department name cannot be empty");
        }

        DepartmentModel departmentModel = convertToEntity(departmentDTO);
        departmentModel = departmentRepository.save(departmentModel);
        return convertToDTO(departmentModel);
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentModel> departments = departmentRepository.findAll();
        if (departments.isEmpty()) {
            throw new ResourceNotFoundException("No departments found");
        }

        return departments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DepartmentDTO getDepartmentById(Long id) {
        Optional<DepartmentModel> department = departmentRepository.findById(id);
        if (department.isEmpty()) {
            throw new ResourceNotFoundException("Department with id " + id + " not found");
        }
        return convertToDTO(department.get());
    }
}
