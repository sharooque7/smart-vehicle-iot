package com.ainzson.smartvehiclemanagement.repository.vehicle;

import com.ainzson.smartvehiclemanagement.entity.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findByVin(String vin);

    Optional<Vehicle> findByVehicleId(Long id);

    List<Vehicle> findByOwnerUserId(Long userId);

    List<Vehicle> findAll();

    void deleteByVehicleId(long id);
}
