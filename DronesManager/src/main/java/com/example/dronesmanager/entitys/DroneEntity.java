package com.example.dronesmanager.entitys;

import com.example.dronesmanager.entitys.products.MedicationEntity;
import com.example.dronesmanager.enums.DronesModel;
import com.example.dronesmanager.enums.DronesState;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

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
public class DroneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100, name = "serialnumber")
    private String serialNumber;

    @Column(nullable = false, name = "model")
    @Enumerated(EnumType.STRING)
    private DronesModel model;

    @Column(nullable = false, name = "weightlimit")
    private Double weightLimit;

    @Column(nullable = false, name = "battery_percent")
    @Audited
    private Double batteryPercent;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DronesState state;

    @OneToMany(mappedBy = "drone")
    private List<MedicationEntity> medications = new ArrayList<>();

}
