package org.example.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;

    private String name;

    private String gender;

    private String dateOfBirth;

    private String graduationDate;

    private Long departmentId;

    private Long managerId;

    private Long teamId;

    private Double grossSalary;

    private Double netSalary;
}
