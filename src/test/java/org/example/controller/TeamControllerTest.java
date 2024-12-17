package org.example.controller;

import org.example.DTO.TeamDTO;
import org.example.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamService teamService;

    @Test
    void testCreateTeam() throws Exception {
        String teamName = "Team A";

        mockMvc.perform(post("/teams").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"" + teamName + "\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value(teamName));

        List<TeamDTO> teams = teamService.getAllTeams();
        assertTrue(teams.stream().anyMatch(t -> t.getName().equals(teamName)));
    }

    @Test
    void testGetAllTeams() throws Exception {
        teamService.saveTeam(new TeamDTO("Team A"));
        teamService.saveTeam(new TeamDTO("Team B"));

        mockMvc.perform(get("/teams")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[0].name").value("Team A")).andExpect(jsonPath("$[1].name").value("Team B"));
    }
}
