package com.example.dronesmanager.dto.requests;

import com.example.dronesmanager.dto.Medication;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class LoadDroneWithMedicationsRequest {
    @NotEmpty(message = "The medications list cannot be empty")
    private List<@NotNull(message = "The medication cannot be empty") UUID> medications;

    public List<UUID> getMedications() {
        return medications;
    }


    public LoadDroneWithMedicationsRequest() {
    }

    public LoadDroneWithMedicationsRequest(List<@NotNull(message = "The medication cannot be empty") UUID> medications) {
        this.medications = medications;
    }

    public void setMedications(List<UUID> medications) {
        this.medications = medications;
    }
}
