package com.example.dronesmanager.repositorys;

import com.example.dronesmanager.entitys.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DroneEntityRepository extends JpaRepository<DroneEntity, UUID>, JpaSpecificationExecutor<DroneEntity> {
    
}