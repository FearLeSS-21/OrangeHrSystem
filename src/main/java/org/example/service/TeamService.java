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

    public TeamDTO saveTeam(@Valid TeamDTO teamDTO) {
        TeamModel teamModel = new TeamModel(null, teamDTO.getName(), null);
        teamModel = teamRepository.save(teamModel);
        return new TeamDTO(teamModel.getName());
    }

    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(teamModel -> new TeamDTO(teamModel.getName()))
                .collect(Collectors.toList());
    }
}
