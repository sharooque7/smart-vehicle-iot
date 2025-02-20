package com.ainzson.smartvehiclemanagement.dto;

import com.ainzson.smartvehiclemanagement.entity.Sensor;
import lombok.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Data
@AllArgsConstructor
public class SensorDto {

    private Instant ts = Instant.now();
    private long sensorId;
    private String sensorType;
    private String sensorName;
    private String status;
    private String tag;
    private long vehicleId;
    private Map<String, Double> telemetry;

    public SensorDto() {}

    public  SensorDto(Sensor sensor,Double telemetry) {
        Map<String, Double> telemetryKeyValuePair = new HashMap<>();
        telemetryKeyValuePair.put(sensor.getTag(),telemetry);

        this.setSensorId(sensor.getSensorId());
        this.setSensorType(sensor.getSensorType());
        this.setSensorName(sensor.getSensorName());
        this.setStatus(sensor.getStatus());
        this.setTag(sensor.getTag());
        this.setTelemetry(telemetryKeyValuePair);
        this.vehicleId = sensor.getVehicle().getVehicleId();
    }

}
