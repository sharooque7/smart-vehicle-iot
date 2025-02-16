package com.ainzson.smartvehiclemanagement.repository.sensor;


import com.ainzson.smartvehiclemanagement.entity.Sensor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public interface SensorMongoRepository extends MongoRepository<Sensor, String>, SensorRepository {

    Optional<Sensor> findById(Long sensorId);

    List<Sensor> findByVehicle_VehicleId(Long vehicleId);

    List<Sensor> findByStatus(String status);
}
