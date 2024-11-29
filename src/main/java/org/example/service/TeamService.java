package org.example.service;

import jakarta.validation.Valid;
import org.example.DTO.TeamDTO;
import org.example.model.TeamModel;
import org.example.repository.TeamRepository;
import org.example.Mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public TeamDTO saveTeam(@Valid TeamDTO teamDTO) {
        TeamModel teamModel = TeamMapper.INSTANCE.toModel(teamDTO);
        teamModel = teamRepository.save(teamModel);
        return TeamMapper.INSTANCE.toDTO(teamModel);
    }

    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(TeamMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TeamModel> getTeamById(Long id) {
        return teamRepository.findById(id);
    }
}
