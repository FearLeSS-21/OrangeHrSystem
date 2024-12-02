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

    @Autowired
    private EmployeeMapper employeeMapper;

    public EmployeeDTO saveEmployee(@Valid EmployeeDTO employeeDTO) {
        EmployeeModel employee = employeeMapper.toModel(employeeDTO);

        if (employeeDTO.getDepartmentId() != null) {
            Optional<DepartmentModel> department = departmentService.getDepartmentById(employeeDTO.getDepartmentId());
            if (department.isPresent()) {
                employee.setDepartment(department.get());
            } else {
                throw new FieldCannotBeNullException("Department not found with ID: " + employeeDTO.getDepartmentId());
            }
        }

        if (employeeDTO.getManagerId() != null) {
            Optional<EmployeeModel> manager = employeeRepository.findById(employeeDTO.getManagerId());
            if (manager.isPresent()) {
                employee.setManager(manager.get());
            } else {
                throw new FieldCannotBeNullException("Manager not found with ID: " + employeeDTO.getManagerId());
            }
        } else {
            employee.setManager(null);
        }

        if (employeeDTO.getTeamId() != null) {
            Optional<TeamModel> team = teamService.getTeamById(employeeDTO.getTeamId());
            if (team.isPresent()) {
                employee.setTeam(team.get());
            } else {
                throw new FieldCannotBeNullException("Team not found with ID: " + employeeDTO.getTeamId());
            }
        }

        employee.calculateNetSalary();

        EmployeeModel savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDTO(savedEmployee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeModel> employees = employeeRepository.findAll();
        return employees.stream().map(employeeMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<EmployeeModel> employee = employeeRepository.findById(id);
        return employee.map(employeeMapper::toDTO);
    }
}
