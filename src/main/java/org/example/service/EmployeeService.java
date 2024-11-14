package org.example.service;

import org.example.DTO.EmployeeDTO;
import org.example.model.EmployeeModel;
import org.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Convert entity to DTO
    private EmployeeDTO convertToDTO(EmployeeModel employeeModel) {
        return new EmployeeDTO(
                employeeModel.getId(),
                employeeModel.getName(),
                employeeModel.getGender(),
                employeeModel.getDateOfBirth(),
                employeeModel.getGraduationDate(),
                employeeModel.getDepartment() != null ? employeeModel.getDepartment().getId() : null,
                employeeModel.getManager() != null ? employeeModel.getManager().getId() : null,
                employeeModel.getTeam() != null ? employeeModel.getTeam().getId() : null,
                employeeModel.getGrossSalary(),
                employeeModel.getNetSalary()
        );
    }

    // Convert DTO to entity
    private EmployeeModel convertToEntity(EmployeeDTO employeeDTO) {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setId(employeeDTO.getId());
        employeeModel.setName(employeeDTO.getName());
        employeeModel.setGender(employeeDTO.getGender());
        employeeModel.setDateOfBirth(employeeDTO.getDateOfBirth());
        employeeModel.setGraduationDate(employeeDTO.getGraduationDate());
        // You can map the department, manager, and team by ID if needed
        employeeModel.setGrossSalary(employeeDTO.getGrossSalary());
        employeeModel.setNetSalary(employeeDTO.getNetSalary());
        return employeeModel;
    }

    // Create or update an employee
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        EmployeeModel employeeModel = convertToEntity(employeeDTO);
        employeeModel.calculateNetSalary();  // Calculate net salary before saving
        employeeModel = employeeRepository.save(employeeModel);
        return convertToDTO(employeeModel);
    }

    // Get all employees
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get employee by ID
    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(this::convertToDTO);
    }

    // Delete employee by ID
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
