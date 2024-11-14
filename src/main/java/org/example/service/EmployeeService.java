package org.example.service;

import org.example.DTO.EmployeeDTO;
import org.example.model.DepartmentModel;
import org.example.model.EmployeeModel;
import org.example.model.TeamModel;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TeamRepository teamRepository;

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        EmployeeModel employee = new EmployeeModel();
        employee.setName(employeeDTO.getName());
        employee.setGender(employeeDTO.getGender());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setGraduationDate(employeeDTO.getGraduationDate());
        employee.setGrossSalary(employeeDTO.getGrossSalary());

        // Set expertise as strings directly
        employee.setExpertise(employeeDTO.getExpertise());

        // Calculate and set net salary
        employee.calculateNetSalary();

        // Optional: Set department, manager, and team if IDs are provided
        if (employeeDTO.getDepartmentId() != null) {
            Optional<DepartmentModel> department = departmentRepository.findById(employeeDTO.getDepartmentId());
            department.ifPresent(employee::setDepartment);
        }

        if (employeeDTO.getManagerId() != null) {
            Optional<EmployeeModel> manager = employeeRepository.findById(employeeDTO.getManagerId());
            manager.ifPresent(employee::setManager);
        }

        if (employeeDTO.getTeamId() != null) {
            Optional<TeamModel> team = teamRepository.findById(employeeDTO.getTeamId());
            team.ifPresent(employee::setTeam);
        }

        EmployeeModel savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeModel> employees = employeeRepository.findAll();
        return employees.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<EmployeeModel> employee = employeeRepository.findById(id);
        return employee.map(this::convertToDTO);
    }

    private EmployeeDTO convertToDTO(EmployeeModel employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setGender(employee.getGender());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setGraduationDate(employee.getGraduationDate());
        dto.setDepartmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null);
        dto.setManagerId(employee.getManager() != null ? employee.getManager().getId() : null);
        dto.setTeamId(employee.getTeam() != null ? employee.getTeam().getId() : null);
        dto.setExpertise(employee.getExpertise());  // Set expertise as strings
        dto.setGrossSalary(employee.getGrossSalary());
        dto.setNetSalary(employee.getNetSalary());
        return dto;
    }
}
