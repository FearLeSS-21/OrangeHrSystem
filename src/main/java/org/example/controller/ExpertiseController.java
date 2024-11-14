package org.example.controller;

import org.example.DTO.ExpertiseDTO;
import org.example.service.ExpertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expertise")
public class ExpertiseController {

    @Autowired
    private ExpertiseService expertiseService;

    // Create expertise
    @PostMapping
    public ResponseEntity<ExpertiseDTO> createExpertise(@RequestBody ExpertiseDTO expertiseDTO) {
        ExpertiseDTO savedExpertise = expertiseService.saveExpertise(expertiseDTO);
        return ResponseEntity.ok(savedExpertise);
    }

    // Get all expertise
    @GetMapping
    public ResponseEntity<List<ExpertiseDTO>> getAllExpertise() {
        List<ExpertiseDTO> expertiseList = expertiseService.getAllExpertise();
        return ResponseEntity.ok(expertiseList);
    }
}
