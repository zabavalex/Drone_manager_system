package com.example.dronesmanager.services.dronesServiceTests;

import com.example.dronesmanager.dto.Drone;
import com.example.dronesmanager.dto.Medication;
import com.example.dronesmanager.entitys.DroneEntity;
import com.example.dronesmanager.entitys.products.MedicationEntity;
import com.example.dronesmanager.enums.DronesModel;
import com.example.dronesmanager.enums.DronesState;
import com.example.dronesmanager.exceptions.BusinessException;
import com.example.dronesmanager.repositorys.DroneEntityRepository;
import com.example.dronesmanager.repositorys.MedicationEntityRepository;
import com.example.dronesmanager.services.DronesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
public class LoadDroneTest {
    @Mock
    private DroneEntityRepository droneRepository = Mockito.mock(DroneEntityRepository.class);
    @Mock
    private MedicationEntityRepository medicationRepository = Mockito.mock(MedicationEntityRepository.class);

    @InjectMocks
    private DronesService service;
    private final UUID normalDrone = UUID.randomUUID();
    private final UUID deadBatteryDrone = UUID.randomUUID();
    private final UUID smallWeightLimitDrone = UUID.randomUUID();
    private final UUID wrongStateDrone = UUID.randomUUID();
    private final List<UUID> foundedMedicates = new ArrayList<>() {{
        add(UUID.randomUUID());
        add(UUID.randomUUID());
    }};
    private final List<UUID> equalsWeightMedicates = new ArrayList<>() {{
        add(UUID.randomUUID());
        add(UUID.randomUUID());
    }};
    private final List<UUID> unfoundedMedicates = new ArrayList<>() {{
        add(UUID.randomUUID());
        add(UUID.randomUUID());
    }};

    @Before
    public void prepareData() {
        DroneEntity droneBuilder = DroneEntity.builder()
                .serialNumber("1")
                .model(DronesModel.MIDDLEWEIGHT)
                .batteryPercent(100.0)
                .weightLimit(200.0)
                .state(DronesState.IDLE).build();
        Mockito.when(droneRepository.findById(deadBatteryDrone)).thenReturn(Optional.of(
                droneBuilder.toBuilder().id(deadBatteryDrone).batteryPercent(24.0).build()
        ));

        Mockito.when(droneRepository.findById(smallWeightLimitDrone)).thenReturn(Optional.of(
                droneBuilder.toBuilder().id(smallWeightLimitDrone).weightLimit(10.0).build()
        ));

        Mockito.when(droneRepository.findById(wrongStateDrone)).thenReturn(Optional.of(
                droneBuilder.toBuilder().id(wrongStateDrone).state(DronesState.LOADING).build()
        ));

        Mockito.when(droneRepository.findById(normalDrone)).thenReturn(Optional.of(
                droneBuilder.toBuilder().id(normalDrone).build()
        ));

        Mockito.when(droneRepository.saveAndFlush(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        MedicationEntity medicationBuilder = MedicationEntity.builder()
                .id(UUID.randomUUID())
                .name("123")
                .weight(10.0)
                .image(null).build();
        Mockito.when(medicationRepository.findAllById(foundedMedicates)).thenReturn(
                new ArrayList<>(){{
                    add(medicationBuilder.toBuilder().id(foundedMedicates.get(0)).build());
                    add(medicationBuilder.toBuilder().id(foundedMedicates.get(1)).build());
                }}
        );

        Mockito.when(medicationRepository.findAllById(unfoundedMedicates)).thenReturn(
                new ArrayList<>()
        );

        Mockito.when(medicationRepository.findAllById(equalsWeightMedicates)).thenReturn(
                new ArrayList<>(){{
                    add(medicationBuilder.toBuilder().id(equalsWeightMedicates.get(0)).weight(100.0).build());
                    add(medicationBuilder.toBuilder().id(equalsWeightMedicates.get(1)).weight(100.0).build());
                }}
        );
    }

    @Test
    public void whenDeadBatteryDrone_ThrowException(){
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> service.loadDroneWithMedications(deadBatteryDrone, foundedMedicates)
        );

        assertEquals("The drone (id : " + deadBatteryDrone + ") has dead battery", thrown.getMessage());
    }

    @Test
    public void whenNoDrone_ThrowException(){
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> service.loadDroneWithMedications(UUID.randomUUID(), foundedMedicates)
        );

        assertEquals("There is no drone with this id in the system", thrown.getMessage());
    }

    @Test
    public void whenNotEnoughCapacity_ThrowException(){
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> service.loadDroneWithMedications(smallWeightLimitDrone, foundedMedicates)
        );

        assertEquals("The drone (id : " + smallWeightLimitDrone + ") cannot be loaded due to insufficient capacity", thrown.getMessage());
    }

    @Test
    public void whenWrongState_ThrowException(){
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> service.loadDroneWithMedications(wrongStateDrone, foundedMedicates)
        );

        assertEquals("The drone (id : " + wrongStateDrone + ") cannot be loaded because it is not on the base", thrown.getMessage());
    }

    @Test
    public void whenEqualsWeight_ok(){
        Drone entity = service.loadDroneWithMedications(normalDrone, equalsWeightMedicates);

        assertEquals(normalDrone, entity.getId());
        assertEquals(equalsWeightMedicates,
                entity.getMedications().stream().map(Medication::getId).collect(Collectors.toList()));
    }

    @Test
    public void whenNoMedications_ThrowException(){
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> service.loadDroneWithMedications(wrongStateDrone, unfoundedMedicates)
        );

        assertEquals("There is no medications with ids: " + unfoundedMedicates, thrown.getMessage());
    }
}
