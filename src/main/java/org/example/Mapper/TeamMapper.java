package org.example.Mapper;

import org.example.DTO.TeamDTO;
import org.example.model.TeamModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamModel toModel(TeamDTO teamDTO);
    TeamDTO toDTO(TeamModel teamModel);
}
