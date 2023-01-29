package com.example.dronesmanager.services;

import com.example.dronesmanager.dto.Drone;
import com.example.dronesmanager.dto.requests.LoadDroneWithMedicationsRequest;
import com.example.dronesmanager.dto.requests.RegistryDroneRequest;
import com.example.dronesmanager.dto.requests.SearchDronesRequest;
import com.example.dronesmanager.entitys.DroneEntity;
import com.example.dronesmanager.entitys.products.MedicationEntity;
import com.example.dronesmanager.enums.DronesState;
import com.example.dronesmanager.mappers.DronesMapper;
import com.example.dronesmanager.repositorys.DroneEntityRepository;
import com.example.dronesmanager.repositorys.MedicationEntityRepository;
import com.example.dronesmanager.repositorys.specifications.DroneSearchSpecificationBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DronesService {
    private DroneEntityRepository droneRepository;
    private MedicationService medicationService;

    private MedicationEntityRepository medicationRepository;

    @Transactional(readOnly = true)
    public List<Drone> searchDrones(SearchDronesRequest request) {
        return droneRepository.findAll(
                DronesMapper.searchRequestToSpecificationBuilder(request)
                        .getSearchDronesByParametersSpecification()
        ).stream().map(DronesMapper::droneEntityToDto).collect(Collectors.toList());
    }

    @Transactional
    public Drone registryDrone(RegistryDroneRequest request) {
        if (
                !droneRepository.findAll(
                        DroneSearchSpecificationBuilder.builder()
                                .serialNumbers(Collections.singletonList(request.getSerialNumber()))
                                .build().getSearchDronesByParametersSpecification()
                ).isEmpty()
        ) throw new RuntimeException("A drone with this serial number is already registered in the system");

        return DronesMapper.droneEntityToDto(droneRepository.save(DronesMapper.registryRequestToDroneEntity(request, null)));
    }

    @Transactional
    public Drone loadDroneWithMedications(UUID droneId, List<UUID> medicationsIds) {
        DroneEntity entity = droneRepository.findById(droneId).orElseThrow(
                () -> new RuntimeException("There is no drone with this id in the system")
        );
        List<MedicationEntity> medications = medicationRepository.findAllById(medicationsIds);
        List<UUID> unfoundedIds = medicationsIds
                .stream().filter(it -> medications.stream().anyMatch(medication -> medication.getId() == it)).collect(Collectors.toList());
        if (!unfoundedIds.isEmpty())
            throw new RuntimeException("There is no medications with id: " + unfoundedIds);
        entity.setMedications(medications);
        return DronesMapper.droneEntityToDto(droneRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<Drone> getAllDroneAvailableForLoading() {
        return droneRepository.findAll(
                DroneSearchSpecificationBuilder.builder()
                        .states(Collections.singletonList(DronesState.IDLE))
                        .minBatteryPercent(25.0)
                        .build().getSearchDronesByParametersSpecification()
        ).stream().map(DronesMapper::droneEntityToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Double getDroneBatteryPercent(UUID droneId) {
        return droneRepository.findById(droneId)
                .orElseThrow(() -> new RuntimeException("There is no drone with id: " + droneId))
                .getBatteryPercent();
    }
}
