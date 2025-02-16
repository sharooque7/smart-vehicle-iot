package com.ainzson.smartvehiclemanagement.repository.vehicle;


import com.ainzson.smartvehiclemanagement.entity.Vehicle;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")  // Active when "mongo" profile is set
public interface VehicleMongoRepository extends MongoRepository<Vehicle, String>, VehicleRepository {

    Optional<Vehicle> findByVin(String vin);

    List<Vehicle> findByOwnerUserId(Long userId);
}
