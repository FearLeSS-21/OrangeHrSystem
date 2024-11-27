package org.example.service;

import org.example.DTO.EmployeeDTO;
import org.example.Exception.FieldCannotBeNullException;
import org.example.model.DepartmentModel;
import org.example.model.EmployeeModel;
import org.example.model.TeamModel;
import org.example.repository.EmployeeRepository;
import org.example.Mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TeamService teamService;

    public EmployeeDTO saveEmployee(@Valid EmployeeDTO employeeDTO) {
        // Validate employee fields
        validateEmployeeFields(employeeDTO);

        // Convert DTO to model using the mapper
        EmployeeModel employee = EmployeeMapper.INSTANCE.toModel(employeeDTO);

        // Set department if departmentId is provided
        if (employeeDTO.getDepartmentId() != null) {
            Optional<DepartmentModel> department = departmentService.getDepartmentById(employeeDTO.getDepartmentId());
            employee.setDepartment(department.orElse(null));
        }

        // Set manager if managerId is provided
        if (employeeDTO.getManagerId() != null) {
            Optional<EmployeeModel> manager = employeeRepository.findById(employeeDTO.getManagerId());
            if (manager.isPresent()) {
                employee.setManager(manager.get());
            } else {
                throw new FieldCannotBeNullException("No manager found with ID: " + employeeDTO.getManagerId());
            }
        } else {
            // If managerId is null, set manager to null explicitly
            employee.setManager(null);
        }

        // Set team if teamId is provided
        if (employeeDTO.getTeamId() != null) {
            Optional<TeamModel> team = teamService.getTeamById(employeeDTO.getTeamId());
            employee.setTeam(team.orElse(null));
        }

        // Calculate net salary
        employee.calculateNetSalary();

        // Save the employee and return the saved employee as DTO
        EmployeeModel savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.INSTANCE.toDTO(savedEmployee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeModel> employees = employeeRepository.findAll();
        return employees.stream()
                .map(EmployeeMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<EmployeeModel> employee = employeeRepository.findById(id);
        return employee.map(EmployeeMapper.INSTANCE::toDTO);
    }

    private void validateEmployeeFields(EmployeeDTO employeeDTO) {
        if (employeeDTO.getName() == null || employeeDTO.getName().isEmpty()) {
            throw new FieldCannotBeNullException("Employee name cannot be null or empty");
        }
        if (employeeDTO.getGender() == null || employeeDTO.getGender().isEmpty()) {
            throw new FieldCannotBeNullException("Gender cannot be null or empty");
        }
        if (employeeDTO.getDateOfBirth() == null) {
            throw new FieldCannotBeNullException("Date of birth cannot be null or empty");
        }
        if (employeeDTO.getGraduationDate() == null) {
            throw new FieldCannotBeNullException("Graduation date cannot be null or empty");
        }
        if (employeeDTO.getExpertise() == null || employeeDTO.getExpertise().isEmpty()) {
            throw new FieldCannotBeNullException("Expertise cannot be null or empty");
        }
        if (employeeDTO.getGrossSalary() == null) {
            throw new FieldCannotBeNullException("Gross salary cannot be null or empty");
        }
    }
}
