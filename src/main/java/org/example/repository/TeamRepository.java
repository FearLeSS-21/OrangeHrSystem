package org.example.repository;


import org.example.model.TeamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamModel, Long> {

    Optional<TeamModel> findByName(String name);
}
