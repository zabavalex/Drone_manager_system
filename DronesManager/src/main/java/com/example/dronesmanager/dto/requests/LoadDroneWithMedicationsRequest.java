package com.example.dronesmanager.dto.requests;

import com.example.dronesmanager.dto.Medication;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class LoadDroneWithMedicationsRequest {
    @NotNull(message = "The drone id cannot be empty")
    private final UUID droneId;
    @NotEmpty(message = "The medications list cannot be empty")
    private final List<@NotNull(message = "The medication cannot be empty") UUID> medications;
}
