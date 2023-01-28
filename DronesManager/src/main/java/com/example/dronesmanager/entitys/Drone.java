package com.example.dronesmanager.entitys;

import com.example.dronesmanager.entitys.products.Medication;
import com.example.dronesmanager.enums.DronesModel;
import com.example.dronesmanager.enums.DronesState;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "drones", schema = "drones_manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String serialNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DronesModel model;

    @Column(nullable = false)
    private Double weightLimit;

    @Column(nullable = false)
    private Double battery_percent;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DronesState state;

    @OneToMany(mappedBy = "drone")
    private List<Medication> medications = new ArrayList<>();

}
