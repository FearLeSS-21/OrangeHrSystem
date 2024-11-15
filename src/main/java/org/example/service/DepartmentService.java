package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTO.DepartmentDTO;
import org.example.model.DepartmentModel;
import org.example.repository.DepartmentRepository;
import org.example.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    private static final ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();

    private DepartmentDTO convertToDTO(DepartmentModel departmentModel) {
        return new DepartmentDTO(departmentModel.getId(), departmentModel.getName());
    }

    private DepartmentModel convertToEntity(DepartmentDTO departmentDTO) {
        return new DepartmentModel(departmentDTO.getId(), departmentDTO.getName());
    }

    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        DepartmentModel departmentModel = convertToEntity(departmentDTO);
        departmentModel = departmentRepository.save(departmentModel);
        return convertToDTO(departmentModel);
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DepartmentDTO> getDepartmentById(Long id) {
        return departmentRepository.findById(id).map(this::convertToDTO);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
