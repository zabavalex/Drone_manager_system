package com.example.dronesmanager.dto.requests;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class RegistryMedicationRequest {
    @NotBlank(message = "The name cannot be empty")
    private final String name;
    @NotNull (message = "The weight cannot be empty")
    @Max(value = 500, message = "weight is more than 500g")
    private final Double weight;
    @NotBlank(message = "The code cannot be empty")
    private final String code;
}
