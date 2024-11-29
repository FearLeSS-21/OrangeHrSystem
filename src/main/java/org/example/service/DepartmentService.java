package org.example.service;

import org.example.DTO.DepartmentDTO;
import org.example.model.DepartmentModel;
import org.example.repository.DepartmentRepository;
import org.example.Mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        DepartmentModel departmentModel = DepartmentMapper.INSTANCE.toModel(departmentDTO);
        departmentModel = departmentRepository.save(departmentModel);
        return DepartmentMapper.INSTANCE.toDTO(departmentModel); // Convert back to DTO after saving
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentModel> departments = departmentRepository.findAll();
        return departments.stream()
                .map(DepartmentMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<DepartmentModel> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }
}
