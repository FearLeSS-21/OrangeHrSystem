package org.example.service;

import jakarta.validation.Valid;
import org.example.DTO.TeamDTO;
import org.example.model.TeamModel;
import org.example.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    private TeamDTO convertToDTO(TeamModel teamModel) {
        return new TeamDTO(teamModel.getName());
    }

    private TeamModel convertToEntity(TeamDTO teamDTO) {
        return new TeamModel(null, teamDTO.getName(), null);
    }

    public TeamDTO saveTeam(@Valid TeamDTO teamDTO) {
        TeamModel teamModel = convertToEntity(teamDTO);
        teamModel = teamRepository.save(teamModel);
        return convertToDTO(teamModel);
    }

    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
