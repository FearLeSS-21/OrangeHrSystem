package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.*;


@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    @NotNull(message = "Status code cannot be null")
    @Positive(message = "Status code must be positive")
    private int statusCode;

    @NotNull(message = "Message cannot be null")
    @Size(min = 1, max = 255, message = "Message must be between 1 and 255 characters")
    private String message;
}
