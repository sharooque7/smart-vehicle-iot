package com.ainzson.smartvehiclemanagement.services;


import com.ainzson.smartvehiclemanagement.entity.Sensor;
import com.ainzson.smartvehiclemanagement.repository.sensor.SensorRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepositoryService sensorRepositoryService;

    @Transactional
    public Sensor registerSensor(Sensor sensor) {
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
