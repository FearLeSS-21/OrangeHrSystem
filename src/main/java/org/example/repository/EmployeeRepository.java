package org.example.repository;

import org.example.model.EmployeeModel;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ComponentScan("org.example.repository")
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {

}
