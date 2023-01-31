package com.example.dronesmanager.services;

import com.example.dronesmanager.dto.Drone;
import com.example.dronesmanager.dto.Medication;
import com.example.dronesmanager.dto.requests.LoadDroneWithMedicationsRequest;
import com.example.dronesmanager.dto.requests.RegistryDroneRequest;
import com.example.dronesmanager.dto.requests.SearchDronesRequest;
import com.example.dronesmanager.entitys.DroneEntity;
import com.example.dronesmanager.entitys.products.MedicationEntity;
import com.example.dronesmanager.enums.DronesState;
import com.example.dronesmanager.exceptions.BusinessException;
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
        ) throw new BusinessException("A drone with this serial number is already registered in the system");

        return DronesMapper.droneEntityToDto(droneRepository.saveAndFlush(DronesMapper.registryRequestToDroneEntity(request, null)));
    }

    @Transactional
    public Drone loadDroneWithMedications(UUID droneId, List<UUID> medicationsIds) {
        DroneEntity entity = droneRepository.findById(droneId).orElseThrow(
                () -> new BusinessException("There is no drone with this id in the system")
        );
        List<MedicationEntity> medications = medicationRepository.findAllById(medicationsIds);

        validateDroneBeforeLoad(entity, medications, medicationsIds);

        entity.setMedications(medications);
        entity.setState(entity.getState().getNext());
        return DronesMapper.droneEntityToDto(droneRepository.saveAndFlush(entity));
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
                .orElseThrow(() -> new BusinessException("There is no drone with id: " + droneId))
                .getBatteryPercent();
    }

    private void validateDroneBeforeLoad(DroneEntity drone,
                                         List<MedicationEntity> medicationsFromDB,
                                         List<UUID> medicationsOnLoad) {
        List<UUID> unfoundedIds = medicationsOnLoad
                .stream().filter(it -> medicationsFromDB.stream()
                        .noneMatch(medicationEntity -> medicationEntity.getId().equals(it)))
                .collect(Collectors.toList());

        if (!unfoundedIds.isEmpty())
            throw new BusinessException("There is no medications with ids: " + unfoundedIds);
        if (drone.getState() != DronesState.IDLE)
            throw new BusinessException("The drone (id : " + drone.getId() + ") cannot be loaded because it is not on the base");
        if (drone.getBatteryPercent() < 25.0)
            throw new BusinessException("The drone (id : " + drone.getId() + ") has dead battery");
        if (medicationsFromDB.stream().mapToDouble(MedicationEntity::getWeight).sum() > drone.getWeightLimit())
            throw new BusinessException("The drone (id : " + drone.getId() + ") cannot be loaded due to insufficient capacity");

    }
}
