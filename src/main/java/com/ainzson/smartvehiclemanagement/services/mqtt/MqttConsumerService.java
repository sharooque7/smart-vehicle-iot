package com.ainzson.smartvehiclemanagement.services.mqtt;

import com.ainzson.smartvehiclemanagement.entity.Sensor;
import com.ainzson.smartvehiclemanagement.repository.sensor.SensorRepositoryService;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MqttConsumerService {
    private final MqttClient mqttClient;
    private final SensorRepositoryService sensorRepositoryService;
    private final List<String> topics = new ArrayList<>();

    public MqttConsumerService(MqttClient mqttClient, SensorRepositoryService sensorRepositoryService) {
        this.mqttClient = mqttClient;
        this.sensorRepositoryService = sensorRepositoryService;
    }

    @PostConstruct
    public void initializeSensorList()  {
        List<Sensor> sensorList = sensorRepositoryService.findAll();
        for (Sensor sensor : sensorList) {
            String topic = sensor.getSensorId() + "/" + sensor.getSensorName() + "/" + sensor.getTag();
            topics.add(topic);
        }

        try {
            // Start consuming after setting up topics
            consumer();
        } catch (MqttException e) {
            log.error("Error starting MQTT consumer: {}", e.getMessage());
        }
    }


    public void consumer() throws MqttException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            MqttSubscription[] mqttSubscription = new MqttSubscription[topics.size()]; // Allocate array

            for (int i = 0; i < topics.size(); i++) {
                mqttSubscription[i] = new MqttSubscription(topics.get(i), 1); // Store in array
            }


            mqttClient.subscribe(mqttSubscription);

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void disconnected(MqttDisconnectResponse mqttDisconnectResponse) {
                    log.info("disconnected");
                }

                @Override
                public void mqttErrorOccurred(MqttException e) {
                    log.error("disconnected");
                }

                @Override
                   public void messageArrived(String topic, MqttMessage message) {
                    log.info("Received message: {} from topic: {}", new String(message.getPayload()), topic);
                   }

                @Override
                public void deliveryComplete(IMqttToken iMqttToken) {

                }

                @Override
                public void connectComplete(boolean b, String s) {

                }

                @Override
                public void authPacketArrived(int i, MqttProperties mqttProperties) {

                }
            });



        } catch (Exception exception) {
            log.error("Something went wrong {}", exception.getLocalizedMessage());
        }
    }
}