package com.ainzson.smartvehiclemanagement.repository.sensor;

import com.ainzson.smartvehiclemanagement.entity.Sensor;

import java.util.List;
import java.util.Optional;

public interface SensorRepository {

    Sensor save(Sensor sensor);

    Optional<Sensor> findById(Long sensorId);

    List<Sensor> findByVehicle_VehicleId(Long vehicleId);  // ✅ Correct format


    List<Sensor> findByStatus(String status);

    List<Sensor> findAll();
}

