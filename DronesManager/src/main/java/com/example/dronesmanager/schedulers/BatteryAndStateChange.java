package com.example.dronesmanager.schedulers;


import com.example.dronesmanager.entitys.DroneEntity;
import com.example.dronesmanager.enums.DronesState;
import com.example.dronesmanager.repositorys.DroneEntityRepository;
import com.example.dronesmanager.repositorys.specifications.DroneSearchSpecificationBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class BatteryAndStateChange {
    private DroneEntityRepository repository;

    @Scheduled(initialDelay = 600, fixedDelay = 6000)
    public void batteryAndStateChangeTask() {
        List<DroneEntity> droneEntities = repository.findAll();
        List<DroneEntity> changedDrones = new ArrayList<>();
        for(DroneEntity entity : droneEntities){
            if(entity.getState().equals(DronesState.IDLE) && entity.getBatteryPercent() < 100) {
                entity.setBatteryPercent(entity.getBatteryPercent() + 1);
                changedDrones.add(entity);
                log.info("Drone's with id: " + entity.getId() + " is charging. Battery percent: " + entity.getBatteryPercent());
            }
            if(!entity.getState().equals(DronesState.IDLE) && entity.getBatteryPercent() > 0) {
                entity.setBatteryPercent(entity.getBatteryPercent() - 1);
                entity.setState(entity.getState().getNext());
                changedDrones.add(entity);
                log.info("Drone's with id: " + entity.getId() + " is discharging. Battery percent: " + entity.getBatteryPercent());
            }
        }
        repository.saveAll(changedDrones);
    }
}
