package com.ainzson.smartvehiclemanagement.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "sensors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id") // ✅ Ensure it matches the database column
    private Long sensorId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // Hibernate-specific annotation
    private Vehicle vehicle;


    @Column(nullable = false, length = 50)
    private String sensorType; // e.g., Engine Temp, Speed, Brake Pressure

    @Column(nullable = false, length = 50)
    private String sensorName; // Name of the TDengine database

    @Column(nullable = false, updatable = false)
    private Instant installedAt = Instant.now();

    @Column(nullable = false, length = 20)
    private String status ; // active, inactive, faulty

    @Column(nullable = false,length = 20)
    private String tag;
}
