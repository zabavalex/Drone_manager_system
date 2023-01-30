package com.example.dronesmanager.controllers;

import com.example.dronesmanager.dto.Drone;
import com.example.dronesmanager.dto.Medication;
import com.example.dronesmanager.dto.requests.LoadDroneWithMedicationsRequest;
import com.example.dronesmanager.dto.requests.RegistryDroneRequest;
import com.example.dronesmanager.dto.requests.SearchDronesRequest;
import com.example.dronesmanager.services.DronesService;
import com.example.dronesmanager.services.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/drones")
@AllArgsConstructor
public class DronesController {
    private DronesService dronesService;
    private MedicationService medicationService;

    @PostMapping("/search")
    public List<Drone> searchDrones(@RequestBody @NotNull @Valid SearchDronesRequest request) {
        return dronesService.searchDrones(request);
    }

    @PostMapping("/{id}/load")
    public Drone loadDroneWithMedication(
            @PathVariable UUID id,
            @RequestBody
            @NotNull(message = "The medications list cannot be empty")
            LoadDroneWithMedicationsRequest request
    ) {
        return dronesService.loadDroneWithMedications(id, request.getMedications());
    }

    @PostMapping("/")
    public Drone registerDrone(@RequestBody @NotNull @Valid RegistryDroneRequest request) {
        return dronesService.registryDrone(request);
    }

    @GetMapping("/available_for_loading")
    public List<Drone> getAvailableForLoadingDrones(){
        return dronesService.getAllDroneAvailableForLoading();
    }

    @GetMapping("/{id}/medications")
    public List<Medication> getAllMedicationsByDroneId(@PathVariable UUID id) {
        return medicationService.getAllByDroneId(id);
    }

    @GetMapping("/{id}")
    public Double getDroneBatteryPercent(@PathVariable UUID id) {
        return dronesService.getDroneBatteryPercent(id);
    }

}
