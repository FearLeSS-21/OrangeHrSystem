package org.example.service;

import org.example.DTO.DepartmentDTO;
import org.example.Exception.ResourceNotFoundException;
import org.example.model.DepartmentModel;
import org.example.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        DepartmentModel departmentModel = new DepartmentModel();
        departmentModel.setId(departmentDTO.getId());
        departmentModel.setName(departmentDTO.getName());

        departmentModel = departmentRepository.save(departmentModel);
        return new DepartmentDTO(departmentModel.getId(), departmentModel.getName());
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentModel> departments = departmentRepository.findAll();
        if (departments.isEmpty()) {
            throw new ResourceNotFoundException("No departments found");
        }

        return departments.stream()
                .map(department -> new DepartmentDTO(department.getId(), department.getName()))
                .collect(Collectors.toList());
    }
}
