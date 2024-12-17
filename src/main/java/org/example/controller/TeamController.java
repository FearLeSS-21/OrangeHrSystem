package org.example.controller;

import jakarta.validation.Valid;
import org.example.DTO.TeamDTO;
import org.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody @Valid TeamDTO teamDTO) {
        return ResponseEntity.ok(teamService.saveTeam(teamDTO));
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }
}
