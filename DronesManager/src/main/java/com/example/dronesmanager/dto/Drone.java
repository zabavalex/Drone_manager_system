package com.example.dronesmanager.dto;

import com.example.dronesmanager.enums.DronesModel;
import com.example.dronesmanager.enums.DronesState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A DTO for the {@link com.example.dronesmanager.entitys.DroneEntity} entity
 */
@AllArgsConstructor
@Builder
@Getter
public class Drone implements Serializable {
    private final UUID id;
    private final String serialNumber;
    private final DronesModel model;
    private final Double weightLimit;
    private final Double batteryPercent;
    private final DronesState state;
    private final List<Medication> medications;
}