package com.example.dronesmanager.mappers;

import com.example.dronesmanager.dto.Medication;
import com.example.dronesmanager.entitys.products.MedicationEntity;

public final class MedicationsMapper {

    public static Medication medicationEntityToDto(MedicationEntity entity) {
        return Medication.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .droneId(entity.getDrone().getId())
                .image(entity.getImage())
                .build();
    }
}
