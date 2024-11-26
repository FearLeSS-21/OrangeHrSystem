package org.example.service;

import org.example.model.TeamModel;
import org.example.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    public void setUp() {
        teamRepository.deleteAll();
    }

    @Test
    public void testSaveTeam() {
        TeamModel team = new TeamModel();
        team.setName("Backend Development");
        TeamModel savedTeam = teamRepository.save(team);
        assertNotNull(savedTeam.getId());
    }



}
