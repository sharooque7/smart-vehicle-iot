package com.ainzson.smartvehiclemanagement.stimulator.sensor;

import com.ainzson.smartvehiclemanagement.entity.Sensor;
import com.ainzson.smartvehiclemanagement.repository.sensor.SensorRepositoryService;
import com.ainzson.smartvehiclemanagement.services.MqttPublisherService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class Engine {

    private final SensorRepositoryService sensorRepositoryService;
    private final MqttPublisherService mqttPublisherService;

    public List<Sensor> sensors = null;
    private final Random random = new Random();


    @PostConstruct
    public void init() {
        sensors = sensorRepositoryService.findAll();
    }

    private static final Random RANDOM = new Random();

    // Store sensor profiles in memory
    private List<Sensor> sensorList;

    // Fetch sensors once and store them in memory
    public void initializeSensors() {
        // Fetch all sensors from the database
        sensorList = sensorRepositoryService.findAll();
        System.out.println("Fetched and cached sensor profiles.");
    }

    public void simulateAndPublishSensorData() {
        // Make sure sensors are initialized
        if (sensorList == null || sensorList.isEmpty()) {
            System.out.println("Sensor profiles are not initialized. Skipping simulation.");
            return;
        }

        for (Sensor sensor : sensorList) {
            String tag = sensor.getTag();
            String sensorName = sensor.getSensorName();
            Long sensorId = sensor.getSensorId();

            // Simulate data based on tag
            Double telemetry = simulateDataByTag(tag);

            // Publish the simulated data to MQTT
            if (telemetry != null) {
                mqttPublisherService.publishSensorData(telemetry, sensor);
            }
        }
    }

    private Double simulateDataByTag(String tag) {
        return switch (tag.toLowerCase()) {
            case "engine_temp" -> RANDOM.nextDouble() * 50;
            case "brake_pressure" -> 1000 + (RANDOM.nextDouble() * 1000);
            case "speed" -> 0 + (RANDOM.nextDouble() * 150);
            case "rpm" -> 1000 + (RANDOM.nextDouble() * 4000);
            default -> null;
        };
    }

    @Scheduled(fixedRate = 5000) // Publish every 5 seconds
    public void simulateSensorData() {

        for (Sensor sensor : sensors) {

        }

        sensorRepositoryService.findAll().forEach(sensor -> {
            sensor.setValue(random.nextDouble() * 100); // Generate random value
            mqttPublisherService.publishSensorData(sensor);
        });
    }
}

