package org.example.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.*;

@Data
public class EmployeeDTO {


    @NotNull(message = "Employee name cannot be null")
    @Size(min = 1, max = 100, message = "Employee name must be between 1 and 100 characters")
    private String name;

    @NotNull(message = "Gender cannot be null")
    @Pattern(regexp = "^(Male|Female)$", message = "Gender must be 'Male', 'Female'")
    private String gender;

    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dateOfBirth;

    @NotNull(message = "Graduation date cannot be null")
    private LocalDate graduationDate;

    @NotNull(message = "Department ID cannot be null")
    private Long departmentId;

    private Long managerId;

    private Long teamId;

    @NotEmpty(message = "Expertise cannot be empty")
    private Set<String> expertise;

    @NotNull(message = "Gross salary cannot be null")
    @Positive(message = "Gross salary must be positive")
    private Double grossSalary;
}
