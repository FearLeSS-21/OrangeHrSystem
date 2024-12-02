package org.example.Mapper;

import org.example.DTO.DepartmentDTO;
import org.example.model.DepartmentModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentModel toModel(DepartmentDTO departmentDTO);
    DepartmentDTO toDTO(DepartmentModel departmentModel);
}
