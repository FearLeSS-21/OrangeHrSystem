package org.example.service;

import org.example.DTO.TeamDTO;
import org.example.Exception.FieldCannotBeNullException;
import org.example.model.TeamModel;
import org.example.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    // Convert TeamModel to TeamDTO
    private TeamDTO convertToDTO(TeamModel teamModel) {
        return new TeamDTO(teamModel.getId(), teamModel.getName());
    }

    // Convert TeamDTO to TeamModel
    private TeamModel convertToEntity(TeamDTO teamDTO) {
        return new TeamModel(teamDTO.getId(), teamDTO.getName(), null);
    }

    // Save team and validate that the name is not null
    public TeamDTO saveTeam(TeamDTO teamDTO) {
        // Validation for name not being null
        if (teamDTO.getName() == null) {
            throw new FieldCannotBeNullException("Name cannot be null");
        }

        TeamModel teamModel = convertToEntity(teamDTO);
        teamModel = teamRepository.save(teamModel);
        return convertToDTO(teamModel);
    }

    // Get all teams
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
