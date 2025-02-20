package com.ainzson.smartvehiclemanagement.controller;


import com.ainzson.smartvehiclemanagement.entity.Sensor;
import com.ainzson.smartvehiclemanagement.services.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    // 🔹 Register a new sensor
    @PostMapping("/{vehicleId}")
    public ResponseEntity<Sensor> registerSensor(@PathVariable Long vehicleId, @RequestBody Sensor sensor) {
        Sensor savedSensor = sensorService.registerSensor(vehicleId, sensor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSensor);

    }

    // 🔹 Get all sensors
    @GetMapping
    public ResponseEntity<List<Sensor>> getAllSensors() {
        return ResponseEntity.ok(sensorService.getAllSensors());
    }

    // 🔹 Get sensor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable Long id) {
        Optional<Sensor> sensor = sensorService.getSensorById(id);
        return sensor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Get sensors by vehicle ID
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<Sensor>> getSensorsByVehicle(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(sensorService.getSensorsByVehicleId(vehicleId));
    }

    // 🔹 Get sensors by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Sensor>> getSensorsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(sensorService.getSensorsByStatus(status));
    }
}
