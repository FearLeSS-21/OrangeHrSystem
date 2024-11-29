package org.example.controller;

import org.example.DTO.TeamDTO;
import org.example.service.TeamService;
import org.example.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        teamRepository.deleteAll();
    }

    @Test
    void testCreateTeam() throws Exception {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("Team A");

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Team A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Team A"));
    }

    @Test
    void testGetAllTeams() throws Exception {
        List<TeamDTO> teams = Arrays.asList(new TeamDTO("Team A"), new TeamDTO("Team B"));

        teamService.saveTeam(teams.get(0));
        teamService.saveTeam(teams.get(1));

        mockMvc.perform(get("/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Team A"))
                .andExpect(jsonPath("$[1].name").value("Team B"));
    }
}
