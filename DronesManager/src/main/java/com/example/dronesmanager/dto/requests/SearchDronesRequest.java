package com.example.dronesmanager.dto.requests;

import com.example.dronesmanager.enums.DronesModel;
import com.example.dronesmanager.enums.DronesState;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SearchDronesRequest {
    private final List<UUID> ids;
    private final List<String> serialNumbers;
    private final List<DronesModel> dronesModels;
    private final Double minWeight;
    private final Double minBatteryPercent;
    private final List<DronesState> states;
}
