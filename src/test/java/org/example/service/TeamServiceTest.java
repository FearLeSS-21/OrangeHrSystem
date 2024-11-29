package org.example.service;

import org.example.DTO.TeamDTO;
import org.example.model.TeamModel;
import org.example.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTeam() {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("Backend Development");

        TeamModel teamModel = new TeamModel();
        teamModel.setId(1L);
        teamModel.setName("Backend Development");

        when(teamRepository.save(any(TeamModel.class))).thenReturn(teamModel);

        TeamDTO savedTeamDTO = teamService.saveTeam(teamDTO);

        assertNotNull(savedTeamDTO);
        assertEquals("Backend Development", savedTeamDTO.getName());
    }

    @Test
    void testGetAllTeams() {
        TeamModel team1 = new TeamModel();
        team1.setId(1L);
        team1.setName("Backend Development");

        TeamModel team2 = new TeamModel();
        team2.setId(2L);
        team2.setName("Frontend Development");

        when(teamRepository.findAll()).thenReturn(Arrays.asList(team1, team2));

        List<TeamDTO> teams = teamService.getAllTeams();

        assertEquals(2, teams.size());
        assertEquals("Backend Development", teams.get(0).getName());
        assertEquals("Frontend Development", teams.get(1).getName());
    }

    @Test
    void testGetTeamById() {
        TeamModel team = new TeamModel();
        team.setId(1L);
        team.setName("Backend Development");

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        Optional<TeamModel> foundTeam = teamService.getTeamById(1L);

        assertTrue(foundTeam.isPresent());
        assertEquals("Backend Development", foundTeam.get().getName());
    }
}
