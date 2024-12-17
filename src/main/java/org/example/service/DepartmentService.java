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

    @Autowired
    private DepartmentMapper departmentMapper;

    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        DepartmentModel departmentModel = departmentMapper.toModel(departmentDTO);
        departmentModel = departmentRepository.save(departmentModel);
        return departmentMapper.toDTO(departmentModel);
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentModel> departments = departmentRepository.findAll();
        return departments.stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<DepartmentModel> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }
}
