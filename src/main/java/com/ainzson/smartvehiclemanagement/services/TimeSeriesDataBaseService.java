package com.ainzson.smartvehiclemanagement.services;

import com.ainzson.smartvehiclemanagement.configurations.TDengineConnector;
import com.ainzson.smartvehiclemanagement.entity.Sensor;
import com.ainzson.smartvehiclemanagement.repository.sensor.SensorRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Service
public class TimeSeriesDataBaseService {

    private  final SensorRepositoryService sensorRepositoryService;
    private final Connection connection;

    public TimeSeriesDataBaseService(@Qualifier("tdEngineConnection") Connection connection,
                                     SensorRepositoryService sensorRepositoryService) {
        this.sensorRepositoryService = sensorRepositoryService;
        this.connection = connection;
    }

    public void createDataBase() {
        try {
            List<Sensor> sensorList = sensorRepositoryService.findAll();

            for (Sensor sensor : sensorList) {
                createDatabaseForSensor(sensor);
                createSuperTable(sensor);
            }
        }
        catch (Exception exception) {
            log.error("Something went wrong {}",exception.getLocalizedMessage());
        }
    }

    public void createDatabaseForSensor(Sensor sensor) {
        String dbName = sensor.getSensorType().replaceAll("\\s+","_").toLowerCase();
        String createDbQuery = "CREATE DATABASE IF NOT EXISTS " + dbName;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createDbQuery);
            log.info("Database '{}' created successfully!", dbName);
        } catch (SQLException e) {
            log.error("Error creating database '{}': {}", dbName, e.getMessage());
        }
    }

    public void createSuperTable(Sensor sensor) {
        String dbName = sensor.getSensorType().replaceAll("\\s+","_").toLowerCase();
        String useDbQuery = "USE " + dbName;

        String createTableQuery = createSuperTableQuery(sensor);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(useDbQuery);
            stmt.execute(createTableQuery);
            log.info("Super table 'telemetry' created successfully in '{}'", dbName);
        } catch (SQLException e) {
            log.error("Error creating super table in '{}': {}", dbName, e.getMessage());
        }
    }

    private static String createSuperTableQuery(Sensor sensor) {
        String STable = sensor.getSensorType().replaceAll("\\s+", "_").toLowerCase();
        String tag = sensor.getTag();

        // Assuming telemetry data is a numeric value
        return "CREATE STABLE " + STable + " (" +
                "ts TIMESTAMP, " +
                tag + " DOUBLE" +  // Assuming telemetry data is a numeric value
                ") TAGS (" +
                "sensor_id INT, " +
                "vehicle_id INT, " +
                "status NCHAR(50), " +
                "sensor_name NCHAR(50), " +
                "sensor_type NCHAR(50), " +
                "telemetry_key NCHAR(50)" +
                ")";
    }
}
