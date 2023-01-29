package com.example.dronesmanager.schedulers;


import com.example.dronesmanager.entitys.DroneEntity;
import com.example.dronesmanager.enums.DronesState;
import com.example.dronesmanager.repositorys.DroneEntityRepository;
import com.example.dronesmanager.repositorys.specifications.DroneSearchSpecificationBuilder;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class BatteryChange {
    private DroneEntityRepository repository;

    @Scheduled(initialDelay = 600, fixedDelay = 6000)
    public void batteryChangeTask() {
        List<DroneEntity> droneEntities = repository.findAll();
        List<DroneEntity> changedDrones = new ArrayList<>();
        for(DroneEntity entity : droneEntities){
            if(entity.getState().equals(DronesState.IDLE) && entity.getBatteryPercent() < 100) {
                entity.setBatteryPercent(entity.getBatteryPercent() + 1);
                changedDrones.add(entity);
            }
            if(!entity.getState().equals(DronesState.IDLE) && entity.getBatteryPercent() > 0) {
                entity.setBatteryPercent(entity.getBatteryPercent() - 1);
                changedDrones.add(entity);
            }
        }
        repository.saveAll(changedDrones);
    }
}
