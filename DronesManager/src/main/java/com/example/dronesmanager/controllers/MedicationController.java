package com.example.dronesmanager.controllers;

import com.example.dronesmanager.services.MedicationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/medications")
public class MedicationController {
    private MedicationService medicationService;

    @GetMapping(
            value = "/medications/{id}/img",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getMedicationImg(@PathVariable UUID id) {
        return medicationService.getImgForMedication(id);
    }

}
