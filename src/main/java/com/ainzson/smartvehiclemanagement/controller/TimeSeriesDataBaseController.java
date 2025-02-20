package com.ainzson.smartvehiclemanagement.controller;


import com.ainzson.smartvehiclemanagement.entity.Sensor;
import com.ainzson.smartvehiclemanagement.repository.sensor.SensorRepositoryService;
import com.ainzson.smartvehiclemanagement.services.TimeSeriesDataBaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/timeseries/database")
public class TimeSeriesDataBaseController {

    private final TimeSeriesDataBaseService timeSeriesDataBaseService;

    public TimeSeriesDataBaseController(TimeSeriesDataBaseService timeSeriesDataBaseService) {
        this.timeSeriesDataBaseService = timeSeriesDataBaseService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDataBase() {
        try {
            timeSeriesDataBaseService.createDataBase();
            return ResponseEntity.ok("Database created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
