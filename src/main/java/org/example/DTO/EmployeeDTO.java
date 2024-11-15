package org.example.DTO;

import lombok.Data;
import java.util.Set;
import jakarta.validation.constraints.*;


@Data
public class EmployeeDTO {

    @NotNull(message = "Employee ID cannot be null")
    private Long id;

    @NotNull(message = "Employee name cannot be null")
    @Size(min = 1, max = 100, message = "Employee name must be between 1 and 100 characters")
    private String name;

    @NotNull(message = "Gender cannot be null")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be 'Male', 'Female' or 'Other'")
    private String gender;

    @NotNull(message = "Date of birth cannot be null")
    private String dateOfBirth;

    @NotNull(message = "Graduation date cannot be null")
    private String graduationDate;

    @NotNull(message = "Department ID cannot be null")
    private Long departmentId;

    private Long managerId;

    @Size(max = 100, message = "Manager name must be less than 100 characters")
    private String managerName;    // New field for manager's name

    private Long teamId;

    @Size(max = 100, message = "Team name must be less than 100 characters")
    private String teamName;       // New field for team's name

    @NotEmpty(message = "Expertise cannot be empty")
    private Set<String> expertise;

    @NotNull(message = "Gross salary cannot be null")
    @Positive(message = "Gross salary must be positive")
    private Double grossSalary;

    @NotNull(message = "Net salary cannot be null")
    @Positive(message = "Net salary must be positive")
    private Double netSalary;
}
