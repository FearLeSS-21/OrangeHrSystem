package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTO.TeamDTO;
import org.example.model.TeamModel;
import org.example.repository.TeamRepository;
import org.example.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    private static final ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();

    private TeamDTO convertToDTO(TeamModel teamModel) {
        return new TeamDTO(teamModel.getId(), teamModel.getName());
    }

    private TeamModel convertToEntity(TeamDTO teamDTO) {
        return new TeamModel(teamDTO.getId(), teamDTO.getName(), null);
    }

    public TeamDTO saveTeam(TeamDTO teamDTO) {
        TeamModel teamModel = convertToEntity(teamDTO);
        teamModel = teamRepository.save(teamModel);
        return convertToDTO(teamModel);
    }

    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TeamDTO> getTeamById(Long id) {
        return teamRepository.findById(id).map(this::convertToDTO);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
