package com.example.dronesmanager.services;

import com.example.dronesmanager.dto.Medication;
import com.example.dronesmanager.entitys.products.MedicationEntity;
import com.example.dronesmanager.mappers.MedicationsMapper;
import com.example.dronesmanager.repositorys.MedicationEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicationService {
    private MedicationEntityRepository medicationRepository;

    @Transactional(readOnly = true)
    public List<Medication> getAllByDroneId(UUID drone) {
        return medicationRepository.findAllByDrone_Id(drone).stream().map(MedicationsMapper::medicationEntityToDto).collect(Collectors.toList());
    }
}
