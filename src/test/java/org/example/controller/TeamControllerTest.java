/*

 */
package org.example.controller;

import org.example.DTO.TeamDTO;
import org.example.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest  // Load the full Spring application context
@AutoConfigureMockMvc  // Automatically configure MockMvc
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Inject MockMvc

    @MockBean
    private TeamService teamService;  // Mock the TeamService bean

    @Test
    void testCreateTeam() throws Exception {
        // Prepare a mock TeamDTO object to return
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("Team A");

        // Simulate service layer response
        when(teamService.saveTeam(teamDTO)).thenReturn(teamDTO);

        // Perform a POST request
        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Team A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Team A"));
    }

    @Test
    void testGetAllTeams() throws Exception {
        // Prepare mock list of teams
        List<TeamDTO> teams = Arrays.asList(
                new TeamDTO("Team A"),
                new TeamDTO("Team B")
        );

        // Simulate service layer response
        when(teamService.getAllTeams()).thenReturn(teams);

        // Perform a GET request
        mockMvc.perform(get("/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Team A"))
                .andExpect(jsonPath("$[1].name").value("Team B"));
    }
}
