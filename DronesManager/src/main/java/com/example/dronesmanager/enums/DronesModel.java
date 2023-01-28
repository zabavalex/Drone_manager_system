package com.example.dronesmanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum DronesModel {
    LIGHTWEIGHT(125),
    MIDDLEWEIGHT(250),
    CRUISERWEIGHT(375),
    HEAVYWEIGHT(500);

    private final double maxWeightLimit;
}
