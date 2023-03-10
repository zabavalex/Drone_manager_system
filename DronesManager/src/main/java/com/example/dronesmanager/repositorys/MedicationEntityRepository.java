package com.example.dronesmanager.repositorys;

import com.example.dronesmanager.entitys.products.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MedicationEntityRepository extends JpaRepository<MedicationEntity, UUID> {
    List<MedicationEntity> findAllByDrone_Id(UUID drone);
}