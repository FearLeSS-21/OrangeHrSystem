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
    private String managerName;    // New field for manager's name
    private Long teamId;
    private String teamName;       // New field for team's name
    private Set<String> expertise;
    private Double grossSalary;
    private Double netSalary;
}
