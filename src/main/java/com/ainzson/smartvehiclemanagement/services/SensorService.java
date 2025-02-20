package com.ainzson.smartvehiclemanagement.services;


import com.ainzson.smartvehiclemanagement.entity.Sensor;
import com.ainzson.smartvehiclemanagement.entity.Vehicle;
import com.ainzson.smartvehiclemanagement.repository.sensor.SensorRepositoryService;
import com.ainzson.smartvehiclemanagement.repository.vehicle.VehicleRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepositoryService sensorRepositoryService;
    private final VehicleRepositoryService vehicleRepositoryService;

    @Transactional
    public Sensor registerSensor(Long vehicleId, Sensor sensor) {


        Vehicle vehicle = vehicleRepositoryService.findByVehicleId(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        sensor.setVehicle(vehicle);
        sensor.setStatus("active"); // Default status on onboarding
        return sensorRepositoryService.save(sensor);
    }

    public List<Sensor> getAllSensors() {
        return sensorRepositoryService.findAll();
    }

    public List<Sensor> getSensorsByVehicleId(Long vehicleId) {
        return sensorRepositoryService.findByVehicle_VehicleId(vehicleId);
    }

    public Optional<Sensor> getSensorById(Long sensorId) {
        return sensorRepositoryService.findById(sensorId);
    }

    public List<Sensor> getSensorsByStatus(String status) {
        return sensorRepositoryService.findByStatus(status);
    }
}
