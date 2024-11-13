package org.example.repository;


import org.example.model.ExpertiseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertiseRepository extends JpaRepository<ExpertiseModel, Long> {

    // Custom query to find expertise by name
    Optional<ExpertiseModel> findByName(String name);
}
