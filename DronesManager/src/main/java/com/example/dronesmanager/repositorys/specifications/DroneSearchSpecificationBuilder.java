package com.example.dronesmanager.repositorys.specifications;

import com.example.dronesmanager.entitys.DroneEntity;
import com.example.dronesmanager.entitys.DroneEntity_;
import com.example.dronesmanager.enums.DronesModel;
import com.example.dronesmanager.enums.DronesState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneSearchSpecificationBuilder {
    private List<UUID> ids;
    private List<String> serialNumbers;
    private List<DronesModel> dronesModels;
    private Double minWeight;
    private Double minBatteryPercent;
    private List<DronesState> states;

    public Specification<DroneEntity> getSearchDronesByParametersSpecification() {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<Predicate>() {{
                if (ids != null && !ids.isEmpty()) add(root.get(DroneEntity_.id).in(ids));
                if (serialNumbers != null && !serialNumbers.isEmpty()) add(root.get(DroneEntity_.serialNumber).in(serialNumbers));
                if (dronesModels != null && !dronesModels.isEmpty()) add(root.get(DroneEntity_.model).in(dronesModels));
                if (minWeight != null) add(cb.greaterThan(root.get(DroneEntity_.weightLimit), minWeight));
                if (minBatteryPercent != null) add(cb.greaterThan(root.get(DroneEntity_.batteryPercent), minBatteryPercent));
                if (states != null && !states.isEmpty()) add(root.get(DroneEntity_.state).in(states));
            }};
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
