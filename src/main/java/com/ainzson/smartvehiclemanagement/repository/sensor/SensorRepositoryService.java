package com.ainzson.smartvehiclemanagement.repository.sensor;


import com.ainzson.smartvehiclemanagement.entity.Sensor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SensorRepositoryService implements SensorRepository {

    private final SensorRepository sensorRepository;

    @Override
    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public Optional<Sensor> findById(Long sensorId) {
        return sensorRepository.findById(sensorId);
    }

    @Override
    public List<Sensor> findByVehicle_VehicleId(Long vehicleId) {
        return null;
    }

    @Override
    public List<Sensor> findByStatus(String status) {
        return sensorRepository.findByStatus(status);
    }

    @Override
    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }
}
