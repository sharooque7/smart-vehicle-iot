package com.ainzson.smartvehiclemanagement.repository.sensor;

import com.ainzson.smartvehiclemanagement.entity.Sensor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("postgres")
public interface SensorJpaRepository extends JpaRepository<Sensor, Long>, SensorRepository {

}
