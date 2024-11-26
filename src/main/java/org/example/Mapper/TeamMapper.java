package org.example.Mapper;

import org.example.DTO.TeamDTO;
import org.example.model.TeamModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeamMapper {
    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    TeamModel toModel(TeamDTO teamDTO);
    TeamDTO toDTO(TeamModel teamModel);
}
