package com.ainzson.smartvehiclemanagement.configurations;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfiguration {

    String broker = "tcp://broker.emqx.io:1883";
    String clientId = "smart-vehicle-management";
    int subQos = 1;
    int pubQos = 1;
    String msg = "Hello MQTT";

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient client = new MqttClient(broker, clientId);
        MqttConnectionOptions options = mqttConnectOptions();
        client.connect(options);
        return  client;

    }

    public MqttConnectionOptions mqttConnectOptions() {
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setServerURIs(new String[]{"tcp://localhost:1884"}); // Change to your EMQX broker
        options.setCleanStart(true);
        options.setAutomaticReconnect(true);
        return options;
    }
}
