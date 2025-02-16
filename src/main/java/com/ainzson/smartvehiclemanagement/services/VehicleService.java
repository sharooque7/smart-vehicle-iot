package com.ainzson.smartvehiclemanagement.services;


import com.ainzson.smartvehiclemanagement.entity.Vehicle;
import com.ainzson.smartvehiclemanagement.repository.vehicle.VehicleRepository;
import com.ainzson.smartvehiclemanagement.repository.vehicle.VehicleRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepositoryService vehicleRepository;

    @Transactional
    public Vehicle registerVehicle(Vehicle vehicle) {
        if (vehicleRepository.findByVin(vehicle.getVin()).isPresent()) {
            throw new RuntimeException("Vehicle with VIN already exists.");
        }
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleByVin(String vin) {
        return vehicleRepository.findByVin(vin);
    }

    public  void deleteVehicle(Long id) {
        vehicleRepository.deleteByVehicleId(id);
    }
}
