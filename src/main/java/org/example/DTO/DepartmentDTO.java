package org.example.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    @NotNull(message = "Department name cannot be null")
    @Size(min = 1, max = 100, message = "Department name must be between 1 and 100 characters")
    private String name;
}
