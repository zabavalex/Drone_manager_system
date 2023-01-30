package com.example.dronesmanager.mappers;

import com.example.dronesmanager.dto.Drone;
import com.example.dronesmanager.dto.requests.RegistryDroneRequest;
import com.example.dronesmanager.dto.requests.SearchDronesRequest;
import com.example.dronesmanager.entitys.DroneEntity;
import com.example.dronesmanager.enums.DronesModel;
import com.example.dronesmanager.enums.DronesState;
import com.example.dronesmanager.repositorys.specifications.DroneSearchSpecificationBuilder;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

public final class DronesMapper {
    public static DroneSearchSpecificationBuilder searchRequestToSpecificationBuilder(SearchDronesRequest request) {
        return DroneSearchSpecificationBuilder.builder()
                .ids(request.getIds())
                .dronesModels(request.getDronesModels())
                .minBatteryPercent(request.getMinBatteryPercent())
                .minWeight(request.getMinWeight())
                .serialNumbers(request.getSerialNumbers())
                .states(request.getStates())
                .build();
    }

    public static Drone droneEntityToDto(DroneEntity entity) {
        return Drone.builder()
                .id(entity.getId())
                .batteryPercent(entity.getBatteryPercent())
                .weightLimit(entity.getWeightLimit())
                .model(entity.getModel())
                .serialNumber(entity.getSerialNumber())
                .state(entity.getState())
                .medications(
                        entity.getMedications() != null
                                ? entity.getMedications().stream()
                                .map(MedicationsMapper::medicationEntityToDto)
                                .collect(Collectors.toList())
                                : null
                )
                .build();
    }

    public static DroneEntity registryRequestToDroneEntity(@NotNull RegistryDroneRequest request, DroneEntity drone) {
        return (
                drone == null
                        ? (
                        DroneEntity.builder()
                                .serialNumber(request.getSerialNumber())
                                .batteryPercent(request.getBatteryPercent() == null ? 100 : request.getBatteryPercent())
                )
                        : (
                        drone.toBuilder()
                                .batteryPercent(request.getBatteryPercent() == null ? drone.getBatteryPercent() : request.getBatteryPercent())
                )
        )
                .weightLimit(request.getWeightLimit())
                .model(DronesModel.getModelByWeightLimit(request.getWeightLimit()))
                .state(DronesState.IDLE)
                .build();
    }
}
