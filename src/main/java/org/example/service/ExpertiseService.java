package org.example.service;

import org.example.DTO.ExpertiseDTO;
import org.example.model.ExpertiseModel;
import org.example.repository.ExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpertiseService {

    @Autowired
    private ExpertiseRepository expertiseRepository;

    // Convert entity to DTO
    private ExpertiseDTO convertToDTO(ExpertiseModel expertiseModel) {
        return new ExpertiseDTO(expertiseModel.getId(), expertiseModel.getName());
    }

    // Convert DTO to entity
    private ExpertiseModel convertToEntity(ExpertiseDTO expertiseDTO) {
        return new ExpertiseModel(expertiseDTO.getId(), expertiseDTO.getName());
    }

    // Create or update expertise
    public ExpertiseDTO saveExpertise(ExpertiseDTO expertiseDTO) {
        ExpertiseModel expertiseModel = convertToEntity(expertiseDTO);
        expertiseModel = expertiseRepository.save(expertiseModel);
        return convertToDTO(expertiseModel);
    }

    // Get all expertise
    public List<ExpertiseDTO> getAllExpertise() {
        return expertiseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get expertise by ID
    public Optional<ExpertiseDTO> getExpertiseById(Long id) {
        return expertiseRepository.findById(id).map(this::convertToDTO);
    }

    // Delete expertise by ID
    public void deleteExpertise(Long id) {
        expertiseRepository.deleteById(id);
    }
}
