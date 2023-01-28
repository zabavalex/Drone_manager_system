package com.example.dronesmanager.entitys.products;

import com.example.dronesmanager.entitys.Drone;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "medications", schema = "drones_manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double weight;
    @Column(nullable = false)
    private String code;
    private UUID image;

    @ManyToOne
    @JoinColumn(name = "drones_id")
    private Drone drone;

}
