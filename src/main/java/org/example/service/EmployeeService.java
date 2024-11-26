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
        validateEmployeeFields(employeeDTO);

        EmployeeModel employee = EmployeeMapper.INSTANCE.toModel(employeeDTO); // Use mapper to convert DTO to model

        // Set relationships like manager, department, and team as before
        if (employeeDTO.getDepartmentId() != null) {
            Optional<DepartmentModel> department = departmentService.getDepartmentById(employeeDTO.getDepartmentId());
            employee.setDepartment(department.orElse(null));
        }

        if (employeeDTO.getManagerId() != null) {
            Optional<EmployeeModel> manager = employeeRepository.findById(employeeDTO.getManagerId());
            if (manager.isPresent()) {
                employee.setManager(manager.get());
            } else {
                throw new FieldCannotBeNullException("No manager found with ID: " + employeeDTO.getManagerId());
            }
        }

        if (employeeDTO.getTeamId() != null) {
            Optional<TeamModel> team = teamService.getTeamById(employeeDTO.getTeamId());
            employee.setTeam(team.orElse(null));
        }

        EmployeeModel savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.INSTANCE.toDTO(savedEmployee); // Convert back to DTO
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeModel> employees = employeeRepository.findAll();
        return employees.stream()
                .map(EmployeeMapper.INSTANCE::toDTO) // Use mapper to convert each model to DTO
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<EmployeeModel> employee = employeeRepository.findById(id);
        return employee.map(EmployeeMapper.INSTANCE::toDTO); // Convert model to DTO if present
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
