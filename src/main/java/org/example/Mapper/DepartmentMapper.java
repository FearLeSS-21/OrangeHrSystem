package org.example.Mapper;

import org.example.DTO.DepartmentDTO;
import org.example.model.DepartmentModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    DepartmentModel toModel(DepartmentDTO departmentDTO);
    DepartmentDTO toDTO(DepartmentModel departmentModel);
}
