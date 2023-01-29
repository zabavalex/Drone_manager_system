package com.example.dronesmanager.services;

import com.example.dronesmanager.dto.Medication;
import com.example.dronesmanager.entitys.products.MedicationEntity;
import com.example.dronesmanager.mappers.MedicationsMapper;
import com.example.dronesmanager.repositorys.MedicationEntityRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
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

    @Transactional(readOnly = true)
    public byte[] getImgForMedication(UUID medicationId) {
        MedicationEntity entity =
                medicationRepository.findById(medicationId)
                        .orElseThrow(() -> new RuntimeException("There is no medication with id: " + medicationId));
        File file = new File("/src/main/resources/" + entity.getImage() + ".jpg");
        try {
            return FileUtils.readFileToByteArray(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("There is no img for medication with id: "+ medicationId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
