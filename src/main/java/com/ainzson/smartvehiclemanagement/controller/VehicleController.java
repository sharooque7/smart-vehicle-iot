package com.ainzson.smartvehiclemanagement.controller;


import com.ainzson.smartvehiclemanagement.entity.Vehicle;
import com.ainzson.smartvehiclemanagement.services.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // 🔹 Register a new vehicle
    @PostMapping
    public ResponseEntity<Vehicle> registerVehicle(@RequestBody Vehicle vehicle) {
        Vehicle savedVehicle = vehicleService.registerVehicle(vehicle);
        return ResponseEntity.ok(savedVehicle);
    }

    // 🔹 Get all vehicles
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    // 🔹 Get vehicle by VIN
    @GetMapping("/{vin}")
    public ResponseEntity<Vehicle> getVehicleByVin(@PathVariable String vin) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleByVin(vin);
        return vehicle.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok("Vehicle deleted successfully.");
    }
}


