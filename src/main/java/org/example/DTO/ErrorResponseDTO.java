package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.*;


@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    private int statusCode;
    private String message;
}
