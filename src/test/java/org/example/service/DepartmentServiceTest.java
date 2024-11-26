package org.example.service;

import org.example.model.DepartmentModel;
import org.example.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class DepartmentServiceTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void setUp() {
        departmentRepository.deleteAll();
    }

    @Test
    public void testSaveDepartment() {
        DepartmentModel department = new DepartmentModel();
        department.setName("IT");
        DepartmentModel savedDepartment = departmentRepository.save(department);
        assertNotNull(savedDepartment.getId());
    }

}
