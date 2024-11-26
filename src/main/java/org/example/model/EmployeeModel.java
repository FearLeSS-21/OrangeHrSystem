package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;


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

    private LocalDate dateOfBirth;
    private LocalDate graduationDate;


    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentModel department;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private EmployeeModel manager;  // Manager is another employee

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private TeamModel team;




    @ElementCollection
    @CollectionTable(name = "employee_expertise", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "expertise")
    private Set<String> expertise = new HashSet<>();

    private Double grossSalary;

    private Double netSalary;


    public void calculateNetSalary() {
        double tax = 0.15 * grossSalary;
        double insurance = 500;
        this.netSalary = grossSalary - tax - insurance;
    }
}
