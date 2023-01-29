package com.example.dronesmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link com.example.dronesmanager.entitys.products.MedicationEntity} entity
 */
@AllArgsConstructor
@Builder
@Getter
public class Medication implements Serializable {
    @NotNull
    private final UUID id;
    @NotBlank
    private final String name;
    @NotNull
    private final Double weight;
    @NotNull
    private final String code;
    @NotNull
    private final UUID image;
    private final UUID droneId;
}