package org.example.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    @NotNull(message = "Team name cannot be null")
    @Size(min = 1, max = 100, message = "Team name must be between 1 and 100 characters")
    private String name;
}
