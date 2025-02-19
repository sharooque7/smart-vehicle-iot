package com.ainzson.smartvehiclemanagement.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MqttPublisherService {

    private final MqttClient mqttClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public MqttPublisherService(MqttClient mqttClient, ObjectMapper objectMapper) {
        this.mqttClient = mqttClient;
        this.objectMapper = objectMapper;
    }

    public void publishSensorData(Double tag,  sensorData) {
        String topic = sensorData.getSensorId() + "/" + sensorData.getSensorName() + "/" + sensorData.getTag();
        try {
            String payload = objectMapper.writeValueAsString(sensorData);
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1);
            mqttClient.publish(topic, message);
            System.out.println("Published to topic: " + topic);
        } catch (MqttException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
