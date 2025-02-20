package com.ainzson.smartvehiclemanagement.services.mqtt;

import com.ainzson.smartvehiclemanagement.dto.SensorDto;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
public class MqttPublisherService {

    private final MqttClient mqttClient;

    public MqttPublisherService(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public void publish(SensorDto sensorDto) throws MqttException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            String topic = sensorDto.getSensorId() + "/" + sensorDto.getSensorName() + "/" + sensorDto.getTag();
            byte[] payload = objectMapper.writeValueAsBytes(sensorDto);
            MqttMessage msg = new MqttMessage(payload);
            msg.setQos(1);
            mqttClient.publish(topic, msg);
            log.info("Published to topic: {}" , topic);
        }
        catch (Exception exception) {
            log.error("Something went wrong {}", exception.getLocalizedMessage());
        }
    }
}
