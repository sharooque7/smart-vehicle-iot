package com.ainzson.smartvehiclemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Foreign Key reference to `users`
    private User owner;

    @Column(nullable = false, unique = true)
    private String vin;  // Vehicle Identification Number (unique)

    @Column(nullable = false)
    private String make; // e.g., Toyota, Tesla

    @Column(nullable = false)
    private String model; // e.g., Corolla, Model S

    @Column(nullable = false)
    private int year; // Manufacturing Year

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Sensor> sensors;

}
