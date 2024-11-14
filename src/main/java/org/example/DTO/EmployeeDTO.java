package org.example.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class EmployeeDTO {

    private Long id;
    private String name;
    private String gender;
    private String dateOfBirth;
    private String graduationDate;
    private Long departmentId;
    private Long managerId;
    private Long teamId;
    private Set<String> expertise;  // Updated to Set<String>
    private Double grossSalary;
    private Double netSalary;
}
