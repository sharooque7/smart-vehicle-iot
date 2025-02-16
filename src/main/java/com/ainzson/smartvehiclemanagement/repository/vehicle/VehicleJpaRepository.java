package com.ainzson.smartvehiclemanagement.repository.vehicle;


import com.ainzson.smartvehiclemanagement.entity.Vehicle;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("postgres")  // Active when "postgres" profile is set
public interface VehicleJpaRepository extends JpaRepository<Vehicle, Long>, VehicleRepository {

}
