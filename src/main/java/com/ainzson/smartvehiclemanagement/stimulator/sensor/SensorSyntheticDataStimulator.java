package com.ainzson.smartvehiclemanagement.stimulator.sensor;

import com.ainzson.smartvehiclemanagement.dto.SensorDto;
import com.ainzson.smartvehiclemanagement.entity.Sensor;
import com.ainzson.smartvehiclemanagement.repository.sensor.SensorRepositoryService;
import com.ainzson.smartvehiclemanagement.services.mqtt.MqttPublisherService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorSyntheticDataStimulator {

    private final SensorRepositoryService sensorRepositoryService;
    private final MqttPublisherService mqttPublisherService;

    private static final Random RANDOM = new Random();
    private List<Sensor> sensorList;


    @PostConstruct
    public void init() {
        sensorList = sensorRepositoryService.findAll();

    }


    // Store sensor profiles in memory

    // Fetch sensors once and store them in memory
    public void initializeSensors() {
        // Fetch all sensors from the database
        sensorList = sensorRepositoryService.findAll();
    }

    public void simulateAndPublishSensorData() throws MqttException {
        // Make sure sensors are initialized
        if (sensorList == null || sensorList.isEmpty()) {
            System.out.println("Sensor profiles are not initialized. Skipping simulation.");
            return;
        }

        for (Sensor sensor : sensorList) {
            log.info("Sensor Details {}", sensor.getSensorName());
            String tag = sensor.getTag();

            // Simulate data based on tag
            Double telemetry = simulateDataByTag(tag);

            SensorDto sensorDto = new SensorDto(sensor,telemetry);

            // Publish the simulated data to MQTT
            if (telemetry != null) {
                mqttPublisherService.publish(sensorDto);
            }
        }
    }

    //Engine Temperature: Between 50°C to 100°C (normal engine operating range).
    //RPM: Between 800 to 5800 RPM (idle to high engine speed).
    //Vibration: Between 0 to 5 units (random scale for vibration levels).
    //Battery Voltage: Between 11.5V to 13.5V (standard vehicle battery voltage).
    //Oil Pressure: Between 20 to 80 PSI (normal oil pressure range).

    private Double simulateDataByTag(String tag) {
        return switch (tag.toLowerCase()) {
            case "engine_temp" -> 50 + (RANDOM.nextDouble() * 50); // 50°C to 100°C
            case "rpm" -> 800 + (RANDOM.nextDouble() * 5000); // 800 to 5800 RPM
            case "vibration" -> RANDOM.nextDouble() * 5; // 0 to 5 (arbitrary vibration unit)
            case "battery_voltage" -> 11.5 + (RANDOM.nextDouble() * 2); // 11.5V to 13.5V
            case "oil_pressure" -> 20 + (RANDOM.nextDouble() * 60); // 20 to 80 PSI
            default -> null;
        };
    }


    @Scheduled(fixedRate = 5000) // Publish every 5 seconds
    public void simulateSensorData() throws MqttException {
        simulateAndPublishSensorData();
    }
}

