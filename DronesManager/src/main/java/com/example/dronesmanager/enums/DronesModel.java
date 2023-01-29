package com.example.dronesmanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum DronesModel {
    LIGHTWEIGHT(125.0),
    MIDDLEWEIGHT(250.0),
    CRUISERWEIGHT(375.0),
    HEAVYWEIGHT(500.0);

    private final Double maxWeightLimit;

    public static DronesModel getModelByWeightLimit(Double weightLimit){
        return
                Arrays.stream(DronesModel.values())
                        .sorted(Comparator.comparing(DronesModel::getMaxWeightLimit))
                        .filter(it -> weightLimit < it.getMaxWeightLimit())
                        .findFirst().orElse(null);
    }
}
