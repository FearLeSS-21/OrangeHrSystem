package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String gender;

    private String dateOfBirth;

    private String graduationDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentModel department;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private EmployeeModel manager;  // Manager is another employee

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamModel team;

    @ManyToMany
    @JoinTable(
            name = "employee_expertise",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "expertise_id"))
    private Set<com.example.hrsystem.models.ExpertiseModel> expertise = new HashSet<>();

    private Double grossSalary;

    private Double netSalary;

    // Method to calculate net salary
    public void calculateNetSalary() {
        double tax = 0.15 * grossSalary;  // 15% tax
        double insurance = 500;  // Fixed insurance amount
        this.netSalary = grossSalary - tax - insurance;
    }
}
