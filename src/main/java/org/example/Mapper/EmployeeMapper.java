package org.example.Mapper;

import org.example.DTO.EmployeeDTO;
import org.example.model.EmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "team.id", target = "teamId")
    EmployeeDTO toDTO(EmployeeModel employee);

    @Mapping(source = "managerId", target = "manager.id")
    @Mapping(source = "departmentId", target = "department.id")
    @Mapping(source = "teamId", target = "team.id")
    EmployeeModel toModel(EmployeeDTO employeeDTO);
}


