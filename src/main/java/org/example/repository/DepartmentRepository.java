package org.example.repository;


import org.example.model.DepartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentModel, Long> {
    Optional<DepartmentModel> findByName(String name);
}
