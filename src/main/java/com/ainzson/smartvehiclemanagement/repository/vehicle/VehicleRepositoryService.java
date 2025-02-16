package com.ainzson.smartvehiclemanagement.repository.vehicle;


import com.ainzson.smartvehiclemanagement.entity.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public class VehicleRepositoryService implements VehicleRepository {

    private final VehicleRepository vehicleRepository;

    public VehicleRepositoryService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Optional<Vehicle> findByVin(String vin) {
        return vehicleRepository.findByVin(vin);
    }

    @Override
    public Optional<Vehicle> findByVehicleId(Long id) {
        return vehicleRepository.findByVehicleId(id);
    }

    @Override
    public List<Vehicle> findByOwnerUserId(Long userId) {
        return vehicleRepository.findByOwnerUserId(userId);
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }



    @Override
    public void deleteByVehicleId(long id) {
        Optional<Vehicle> vehicle = vehicleRepository.findByVehicleId(id);

        if (vehicle.isEmpty()) {
            throw new RuntimeException("Vehicle not found with ID: " + id);
        }
        vehicleRepository.deleteByVehicleId(id);
    }

}
