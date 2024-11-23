package org.example.service;

import org.example.DTO.EmployeeDTO;
import org.example.Exception.FieldCannotBeNullException;
import org.example.model.DepartmentModel;
import org.example.model.EmployeeModel;
import org.example.model.TeamModel;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.TeamRepository;
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
    private DepartmentRepository departmentRepository;

    @Autowired
    private TeamRepository teamRepository;

    public EmployeeDTO saveEmployee(@Valid EmployeeDTO employeeDTO) {
        validateEmployeeFields(employeeDTO);

        EmployeeModel employee = new EmployeeModel();
        employee.setName(employeeDTO.getName());
        employee.setGender(employeeDTO.getGender());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setGraduationDate(employeeDTO.getGraduationDate());
        employee.setGrossSalary(employeeDTO.getGrossSalary());
        employee.setExpertise(employeeDTO.getExpertise());
        employee.calculateNetSalary();

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
        if (employeeDTO.getDepartmentId() == null) {
            throw new FieldCannotBeNullException("Department ID cannot be null");
        }
        if (employeeDTO.getExpertise() == null || employeeDTO.getExpertise().isEmpty()) {
            throw new FieldCannotBeNullException("Expertise cannot be null or empty");
        }
        if (employeeDTO.getGrossSalary() == null) {
            throw new FieldCannotBeNullException("Gross salary cannot be null or empty");
        }
    }
    private EmployeeDTO convertToDTO(EmployeeModel employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(employee.getName());
        employeeDTO.setGender(employee.getGender());
        employeeDTO.setDateOfBirth(employee.getDateOfBirth());
        employeeDTO.setGraduationDate(employee.getGraduationDate());
        employeeDTO.setGrossSalary(employee.getGrossSalary());
        employeeDTO.setExpertise(employee.getExpertise());
        return employeeDTO;
    }
}
