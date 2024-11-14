package org.example.service;

import org.example.DTO.TeamDTO;
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

    // Convert entity to DTO
    private TeamDTO convertToDTO(TeamModel teamModel) {
        return new TeamDTO(teamModel.getId(), teamModel.getName());
    }

    // Convert DTO to entity
    private TeamModel convertToEntity(TeamDTO teamDTO) {
        return new TeamModel(teamDTO.getId(), teamDTO.getName(), null);
    }

    // Create or update a team
    public TeamDTO saveTeam(TeamDTO teamDTO) {
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

    // Get team by ID
    public Optional<TeamDTO> getTeamById(Long id) {
        return teamRepository.findById(id).map(this::convertToDTO);
    }

    // Delete team by ID
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
