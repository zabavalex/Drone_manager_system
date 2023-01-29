package com.example.dronesmanager.dto.requests;

import com.example.dronesmanager.enums.DronesModel;
import com.example.dronesmanager.enums.DronesState;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.*;

@Data
public class RegistryDroneRequest {
    @Size(min = 1, max = 100, message = "The size of the serial number cannot be less than 1 and more than 100 characters")
    private final String serialNumber;

    @NotNull(message = "The weight limit cannot be empty")
    @Positive(message = "The weight limit cannot be negative")
    @Max(value = 500, message = "The weight limit cannot be more than 500g")
    private final Double weightLimit;

    @Positive(message = "The battery percent cannot be negative")
    @Max(value = 100, message = "The battery percent cannot be more than 100%")
    private final Double batteryPercent;
}
