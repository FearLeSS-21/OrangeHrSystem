package org.example.service;

import org.example.DTO.TeamDTO;
import org.example.Mapper.TeamMapper;
import org.example.model.TeamModel;
import org.example.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveTeamSuccessfully() {
        TeamDTO teamDTO = new TeamDTO();
        TeamModel teamModel = new TeamModel();
        when(teamMapper.toModel(teamDTO)).thenReturn(teamModel);
        when(teamRepository.save(teamModel)).thenReturn(teamModel);
        when(teamMapper.toDTO(teamModel)).thenReturn(teamDTO);

        TeamDTO result = teamService.saveTeam(teamDTO);

        assertNotNull(result);
        verify(teamRepository).save(teamModel);
    }

    @Test
    public void returnAllTeamsSuccessfully() {
        TeamModel teamModel = new TeamModel();
        TeamDTO teamDTO = new TeamDTO();
        when(teamRepository.findAll()).thenReturn(List.of(teamModel));
        when(teamMapper.toDTO(teamModel)).thenReturn(teamDTO);

        List<TeamDTO> result = teamService.getAllTeams();

        assertEquals(1, result.size());
        verify(teamRepository).findAll();
    }

    @Test
    public void returnTeamByIdSuccessfully() {
        Long teamId = 1L;
        TeamModel teamModel = new TeamModel();
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(teamModel));

        Optional<TeamModel> result = teamService.getTeamById(teamId);

        assertTrue(result.isPresent());
        assertEquals(teamModel, result.get());
        verify(teamRepository).findById(teamId);
    }
}
