package org.example.repository;

import org.example.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {

    // Custom query to find employee by name
    Optional<EmployeeModel> findByName(String name);

    // Custom query to find an employee by manager (optional)
    Optional<EmployeeModel> findByManager(EmployeeModel manager);
}
